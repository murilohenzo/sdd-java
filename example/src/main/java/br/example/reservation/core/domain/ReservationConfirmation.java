package br.example.reservation.core.domain;

public record ReservationConfirmation(Reservation reservation, boolean replay) {
}
