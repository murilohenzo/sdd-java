package br.example.reservation.core.application.port.outbound;

import java.util.function.Function;

import br.example.reservation.core.domain.ReservationConfirmation;
import br.example.reservation.core.domain.ReservationLedger;

public interface ReservationStore {

    /**
     * Applies one reservation operation synchronously with exclusive access to the ledger.
     */
    ReservationConfirmation withExclusiveAccess(Function<ReservationLedger, ReservationConfirmation> operation);
}
