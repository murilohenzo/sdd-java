package br.example.reservation.adapters.inbound.http;

import java.util.UUID;

import br.example.reservation.core.application.port.inbound.ReserveStock;
import br.example.reservation.core.domain.Reservation;
import br.example.reservation.core.domain.ReservationConfirmation;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReservationController {

    private final ReserveStock reserveStock;

    ReservationController(ReserveStock reserveStock) {
        this.reserveStock = reserveStock;
    }

    @PostMapping(path = "/reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationResponse> reserve(
            @RequestHeader(value = "Idempotency-Key", required = false) String key,
            @RequestBody JsonNode body) {
        ReservationHttpRequest request = ReservationHttpRequest.parse(key, body);
        ReservationConfirmation confirmation = reserveStock.reserve(request.key(), request.sku(), request.quantity());
        HttpStatus status = confirmation.replay() ? HttpStatus.OK : HttpStatus.CREATED;
        Reservation reservation = confirmation.reservation();
        return ResponseEntity.status(status)
                .body(new ReservationResponse(reservation.id(), reservation.sku(), reservation.quantity()));
    }

    private record ReservationResponse(UUID id, String sku, int quantity) {
    }
}
