package br.example.reservation.adapters.outbound.memory;

import java.util.function.Function;

import br.example.reservation.core.application.port.outbound.ReservationStore;
import br.example.reservation.core.domain.ReservationConfirmation;
import br.example.reservation.core.domain.ReservationLedger;

public final class InMemoryReservationStore implements ReservationStore {

    private final ReservationLedger ledger;

    public InMemoryReservationStore(ReservationLedger ledger) {
        this.ledger = ledger;
    }

    @Override
    public synchronized ReservationConfirmation withExclusiveAccess(
            Function<ReservationLedger, ReservationConfirmation> operation) {
        return operation.apply(ledger);
    }
}
