package br.example.reservation.core.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static br.example.reservation.core.domain.ReservationRejected.Reason.INSUFFICIENT_STOCK;
import static br.example.reservation.core.domain.ReservationRejected.Reason.KEY_REUSED;
import static br.example.reservation.core.domain.ReservationRejected.Reason.SKU_NOT_FOUND;

public final class ReservationLedger {

    private final String stockedSku;
    private final Map<String, Reservation> confirmations = new HashMap<>();
    private int available;

    public ReservationLedger(String stockedSku, int initialStock) {
        this.stockedSku = Objects.requireNonNull(stockedSku);
        if (initialStock < 0) {
            throw new IllegalArgumentException("Initial stock must be nonnegative");
        }
        this.available = initialStock;
    }

    public ReservationConfirmation reserve(String key, String sku, int quantity) {
        if (Objects.requireNonNull(key).isBlank() || quantity < 1) {
            throw new IllegalArgumentException("Reservation requires a key and a positive quantity");
        }

        Reservation previous = confirmations.get(key);
        if (previous != null) {
            if (!previous.sku().equals(sku) || previous.quantity() != quantity) {
                throw new ReservationRejected(KEY_REUSED);
            }
            return new ReservationConfirmation(previous, true);
        }
        if (!stockedSku.equals(sku)) {
            throw new ReservationRejected(SKU_NOT_FOUND);
        }
        if (quantity > available) {
            throw new ReservationRejected(INSUFFICIENT_STOCK);
        }

        Reservation reservation = new Reservation(UUID.randomUUID(), sku, quantity);
        confirmations.put(key, reservation);
        available -= quantity;
        return new ReservationConfirmation(reservation, false);
    }
}
