package br.example.reservation.core.domain;

public final class ReservationRejected extends RuntimeException {

    private final Reason reason;

    ReservationRejected(Reason reason) {
        super(reason.name());
        this.reason = reason;
    }

    public Reason reason() {
        return reason;
    }

    public enum Reason {
        KEY_REUSED,
        SKU_NOT_FOUND,
        INSUFFICIENT_STOCK
    }
}
