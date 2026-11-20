package br.example.reservation.core.application;

import br.example.reservation.core.application.port.inbound.ReserveStock;
import br.example.reservation.core.application.port.outbound.ReservationStore;
import br.example.reservation.core.domain.ReservationConfirmation;

public final class ReserveStockService implements ReserveStock {

    private final ReservationStore store;

    public ReserveStockService(ReservationStore store) {
        this.store = store;
    }

    @Override
    public ReservationConfirmation reserve(String key, String sku, int quantity) {
        return store.withExclusiveAccess(ledger -> ledger.reserve(key, sku, quantity));
    }
}
