package br.example.reservation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
class ReservationService {

    private static final String SAMPLE_SKU = "DEMO-1";
    private static final int STARTING_STOCK = 10;

    private final Map<String, Reservation> confirmations = new HashMap<>();
    private int available = STARTING_STOCK;

    synchronized Confirmation reserve(String key, String sku, int quantity) {
        Reservation previous = confirmations.get(key);
        if (previous != null) {
            if (!previous.sku().equals(sku) || previous.quantity() != quantity) {
                throw new Rejected(RejectionReason.KEY_REUSED);
            }
            return new Confirmation(previous, true);
        }
        if (!SAMPLE_SKU.equals(sku)) {
            throw new Rejected(RejectionReason.SKU_NOT_FOUND);
        }
        if (quantity > available) {
            throw new Rejected(RejectionReason.INSUFFICIENT_STOCK);
        }

        Reservation reservation = new Reservation(UUID.randomUUID(), sku, quantity);
        confirmations.put(key, reservation);
        available -= quantity;
        return new Confirmation(reservation, false);
    }

    record Reservation(UUID id, String sku, int quantity) {
    }

    record Confirmation(Reservation reservation, boolean replay) {
    }

    enum RejectionReason {
        KEY_REUSED,
        SKU_NOT_FOUND,
        INSUFFICIENT_STOCK
    }

    static final class Rejected extends RuntimeException {
        private final RejectionReason reason;

        Rejected(RejectionReason reason) {
            super(reason.name());
            this.reason = reason;
        }

        RejectionReason reason() {
            return reason;
        }
    }
}
