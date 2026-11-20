package br.example.reservation;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import br.example.reservation.adapters.outbound.memory.InMemoryReservationStore;
import br.example.reservation.core.application.ReserveStockService;
import br.example.reservation.core.application.port.inbound.ReserveStock;
import br.example.reservation.core.domain.Reservation;
import br.example.reservation.core.domain.ReservationConfirmation;
import br.example.reservation.core.domain.ReservationLedger;
import br.example.reservation.core.domain.ReservationRejected;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static br.example.reservation.core.domain.ReservationRejected.Reason.INSUFFICIENT_STOCK;
import static br.example.reservation.core.domain.ReservationRejected.Reason.KEY_REUSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReserveStockServiceTest {

    private static final String SKU = "DEMO-1";
    private final ReserveStock service = new ReserveStockService(
            new InMemoryReservationStore(new ReservationLedger(SKU, 10)));

    @Test
    void firstConfirmationDebitsExactlySix() {
        ReservationConfirmation confirmation = service.reserve("first", SKU, 6);

        assertThat(confirmation.replay()).isFalse();
        assertThat(confirmation.reservation().quantity()).isEqualTo(6);
        assertExactlyFourRemain();
    }

    @Test
    void replayReturnsTheSameConfirmationWithoutDebitingAgain() {
        ReservationConfirmation first = service.reserve("first", SKU, 6);

        ReservationConfirmation replay = service.reserve("first", SKU, 6);

        assertThat(replay.replay()).isTrue();
        assertThat(replay.reservation()).isEqualTo(first.reservation());
        assertExactlyFourRemain();
    }

    @ParameterizedTest
    @CsvSource({"OTHER-1, 6", "DEMO-1, 5"})
    void keyConflictPreservesTheOriginalReservationAndStock(String sku, int quantity) {
        Reservation original = service.reserve("first", SKU, 6).reservation();

        ReservationRejected rejection = assertThrows(ReservationRejected.class,
                () -> service.reserve("first", sku, quantity));

        assertThat(rejection.reason()).isEqualTo(KEY_REUSED);
        assertThat(service.reserve("first", SKU, 6).reservation()).isEqualTo(original);
        assertExactlyFourRemain();
    }

    @ParameterizedTest
    @CsvSource({"OTHER-1, 1, SKU_NOT_FOUND", "DEMO-1, 11, INSUFFICIENT_STOCK"})
    void rejectedReservationLeavesTheFullStockAndItsKeyAvailable(
            String sku, int quantity, ReservationRejected.Reason expected) {
        ReservationRejected rejection = assertThrows(ReservationRejected.class,
                () -> service.reserve("retry", sku, quantity));

        assertThat(rejection.reason()).isEqualTo(expected);
        ReservationRejected aboveInitialStock = assertThrows(ReservationRejected.class,
                () -> service.reserve("too-large", SKU, 11));
        assertThat(aboveInitialStock.reason()).isEqualTo(INSUFFICIENT_STOCK);
        ReservationConfirmation confirmation = service.reserve("retry", SKU, 10);
        assertThat(confirmation.replay()).isFalse();
        assertThat(confirmation.reservation().quantity()).isEqualTo(10);
    }

    @Test
    void applicationPortRejectsNonPositiveQuantityWithoutChangingStock() {
        assertThrows(IllegalArgumentException.class, () -> service.reserve("invalid", SKU, -1));

        assertThat(service.reserve("invalid", SKU, 10).reservation().quantity()).isEqualTo(10);
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
        ReservationRejected rejection = assertThrows(ReservationRejected.class,
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
        } catch (ReservationRejected rejection) {
            return new Attempt(null, rejection.reason());
        }
    }

    private record Attempt(
            ReservationConfirmation confirmation, ReservationRejected.Reason reason) {
    }
}
