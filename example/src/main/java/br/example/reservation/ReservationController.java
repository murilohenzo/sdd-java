package br.example.reservation;

import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ReservationController {

    private static final Pattern KEY_FORMAT = Pattern.compile("[A-Za-z0-9_-]{1,64}");
    private static final Pattern SKU_FORMAT = Pattern.compile("[A-Z0-9-]{1,40}");

    private final ReservationService service;

    ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping(path = "/reservations", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReservationService.Reservation> reserve(
            @RequestHeader(value = "Idempotency-Key", required = false) String key,
            @RequestBody JsonNode body) {
        JsonNode sku = body.path("sku");
        JsonNode quantity = body.path("quantity");
        if (key == null || !KEY_FORMAT.matcher(key).matches()
                || !sku.isTextual() || !SKU_FORMAT.matcher(sku.textValue()).matches()
                || !quantity.isIntegralNumber() || !quantity.canConvertToInt() || quantity.intValue() <= 0) {
            throw new InvalidRequest();
        }

        ReservationService.Confirmation confirmation = service.reserve(key, sku.textValue(), quantity.intValue());
        HttpStatus status = confirmation.replay() ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(confirmation.reservation());
    }

    @ExceptionHandler({InvalidRequest.class, HttpMessageNotReadableException.class})
    ResponseEntity<ApiError> invalidRequest() {
        return ResponseEntity.badRequest().body(new ApiError("INVALID_REQUEST"));
    }

    @ExceptionHandler(ReservationService.Rejected.class)
    ResponseEntity<ApiError> rejected(ReservationService.Rejected exception) {
        HttpStatus status = switch (exception.reason()) {
            case SKU_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case KEY_REUSED, INSUFFICIENT_STOCK -> HttpStatus.CONFLICT;
        };
        return ResponseEntity.status(status).body(new ApiError(exception.reason().name()));
    }

    private record ApiError(String code) {
    }

    private static final class InvalidRequest extends RuntimeException {
    }
}
