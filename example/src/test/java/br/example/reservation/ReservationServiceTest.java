package br.example.reservation;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static br.example.reservation.ReservationService.RejectionReason.INSUFFICIENT_STOCK;
import static br.example.reservation.ReservationService.RejectionReason.KEY_REUSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationServiceTest {

    private static final String SKU = "DEMO-1";
    private final ReservationService service = new ReservationService();

    @Test
    void firstConfirmationDebitsExactlySix() {
        ReservationService.Confirmation confirmation = service.reserve("first", SKU, 6);

        assertThat(confirmation.replay()).isFalse();
        assertThat(confirmation.reservation().quantity()).isEqualTo(6);
        assertExactlyFourRemain();
    }

    @Test
    void replayReturnsTheSameConfirmationWithoutDebitingAgain() {
        ReservationService.Confirmation first = service.reserve("first", SKU, 6);

        ReservationService.Confirmation replay = service.reserve("first", SKU, 6);

        assertThat(replay.replay()).isTrue();
        assertThat(replay.reservation()).isEqualTo(first.reservation());
        assertExactlyFourRemain();
    }

    @ParameterizedTest
    @CsvSource({"OTHER-1, 6", "DEMO-1, 5"})
    void keyConflictPreservesTheOriginalReservationAndStock(String sku, int quantity) {
        ReservationService.Reservation original = service.reserve("first", SKU, 6).reservation();

        ReservationService.Rejected rejection = assertThrows(ReservationService.Rejected.class,
                () -> service.reserve("first", sku, quantity));

        assertThat(rejection.reason()).isEqualTo(KEY_REUSED);
        assertThat(service.reserve("first", SKU, 6).reservation()).isEqualTo(original);
        assertExactlyFourRemain();
    }

    @ParameterizedTest
    @CsvSource({"OTHER-1, 1, SKU_NOT_FOUND", "DEMO-1, 11, INSUFFICIENT_STOCK"})
    void rejectedReservationLeavesTheFullStockAndItsKeyAvailable(
            String sku, int quantity, ReservationService.RejectionReason expected) {
        ReservationService.Rejected rejection = assertThrows(ReservationService.Rejected.class,
                () -> service.reserve("retry", sku, quantity));

        assertThat(rejection.reason()).isEqualTo(expected);
        ReservationService.Rejected aboveInitialStock = assertThrows(ReservationService.Rejected.class,
                () -> service.reserve("too-large", SKU, 11));
        assertThat(aboveInitialStock.reason()).isEqualTo(INSUFFICIENT_STOCK);
        ReservationService.Confirmation confirmation = service.reserve("retry", SKU, 10);
        assertThat(confirmation.replay()).isFalse();
        assertThat(confirmation.reservation().quantity()).isEqualTo(10);
    }

    @Test
    void competingKeysCannotOversellStock() throws Exception {
        List<Attempt> attempts = reserveConcurrently("first", "second");

        assertThat(attempts).extracting(Attempt::reason).containsExactlyInAnyOrder(null, INSUFFICIENT_STOCK);
        assertThat(attempts).extracting(Attempt::confirmation).filteredOn(confirmation -> confirmation != null)
                .singleElement().extracting(confirmation -> confirmation.reservation().quantity()).isEqualTo(6);
        assertExactlyFourRemain();
    }

    @Test
    void competingRequestsWithTheSameKeyConfirmOnce() throws Exception {
        List<Attempt> attempts = reserveConcurrently("same", "same");

        assertThat(attempts).extracting(Attempt::reason).containsOnlyNulls();
        assertThat(attempts).extracting(attempt -> attempt.confirmation().replay())
                .containsExactlyInAnyOrder(true, false);
        assertThat(attempts.get(0).confirmation().reservation())
                .isEqualTo(attempts.get(1).confirmation().reservation());
        assertExactlyFourRemain();
    }

    private void assertExactlyFourRemain() {
        ReservationService.Rejected rejection = assertThrows(ReservationService.Rejected.class,
                () -> service.reserve("five", SKU, 5));
        assertThat(rejection.reason()).isEqualTo(INSUFFICIENT_STOCK);
        assertThat(service.reserve("four", SKU, 4).reservation().quantity()).isEqualTo(4);
    }

    private List<Attempt> reserveConcurrently(String firstKey, String secondKey) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CyclicBarrier startTogether = new CyclicBarrier(2);
        try {
            Future<Attempt> first = executor.submit(() -> attempt(startTogether, firstKey));
            Future<Attempt> second = executor.submit(() -> attempt(startTogether, secondKey));
            return List.of(first.get(10, TimeUnit.SECONDS), second.get(10, TimeUnit.SECONDS));
        } finally {
            executor.shutdownNow();
        }
    }

    private Attempt attempt(CyclicBarrier startTogether, String key) throws Exception {
        startTogether.await(5, TimeUnit.SECONDS);
        try {
            return new Attempt(service.reserve(key, SKU, 6), null);
        } catch (ReservationService.Rejected rejection) {
            return new Attempt(null, rejection.reason());
        }
    }

    private record Attempt(
            ReservationService.Confirmation confirmation, ReservationService.RejectionReason reason) {
    }
}
