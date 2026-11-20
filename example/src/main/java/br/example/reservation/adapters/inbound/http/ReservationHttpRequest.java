package br.example.reservation.adapters.inbound.http;

import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

record ReservationHttpRequest(String key, String sku, int quantity) {

    private static final Pattern KEY_FORMAT = Pattern.compile("[A-Za-z0-9_-]{1,64}");
    private static final Pattern SKU_FORMAT = Pattern.compile("[A-Z0-9-]{1,40}");

    static ReservationHttpRequest parse(String key, JsonNode body) {
        JsonNode sku = body.path("sku");
        JsonNode quantity = body.path("quantity");
        if (key == null || !KEY_FORMAT.matcher(key).matches()
                || !sku.isTextual() || !SKU_FORMAT.matcher(sku.textValue()).matches()
                || !quantity.isIntegralNumber() || !quantity.canConvertToInt() || quantity.intValue() <= 0) {
            throw new InvalidRequest();
        }
        return new ReservationHttpRequest(key, sku.textValue(), quantity.intValue());
    }

    static final class InvalidRequest extends RuntimeException {
    }
}
