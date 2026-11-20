package br.example.reservation.core.domain;

import java.util.UUID;

public record Reservation(UUID id, String sku, int quantity) {
}
