package br.example.reservation.adapters.inbound.http;

import br.example.reservation.core.domain.ReservationRejected;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ReservationController.class)
class ReservationHttpErrors {

    @ExceptionHandler({ReservationHttpRequest.InvalidRequest.class, HttpMessageNotReadableException.class})
    ResponseEntity<ApiError> invalidRequest() {
        return ResponseEntity.badRequest().body(new ApiError("INVALID_REQUEST"));
    }

    @ExceptionHandler(ReservationRejected.class)
    ResponseEntity<ApiError> rejected(ReservationRejected exception) {
        return switch (exception.reason()) {
            case SKU_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("SKU_NOT_FOUND"));
            case KEY_REUSED -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError("KEY_REUSED"));
            case INSUFFICIENT_STOCK -> ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError("INSUFFICIENT_STOCK"));
        };
    }

    private record ApiError(String code) {
    }
}
