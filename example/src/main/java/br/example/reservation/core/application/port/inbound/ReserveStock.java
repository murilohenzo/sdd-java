package br.example.reservation.core.application.port.inbound;

import br.example.reservation.core.domain.ReservationConfirmation;

public interface ReserveStock {

    ReservationConfirmation reserve(String key, String sku, int quantity);
}
