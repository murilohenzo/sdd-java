package br.example.reservation;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationHttpTest {

    @Autowired
    private TestRestTemplate client;

    @Test
    void exposesReservationReplayAndErrorsOverHttp() {
        assertError(reserve("unknown", "OTHER-1", 1), HttpStatus.NOT_FOUND, "SKU_NOT_FOUND");
        assertError(reserve("too-large", "DEMO-1", 11), HttpStatus.CONFLICT, "INSUFFICIENT_STOCK");

        ResponseEntity<JsonNode> first = reserve("first", "DEMO-1", 6);
        assertThat(first.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        JsonNode original = first.getBody();
        assertThat(original).isNotNull();
        assertThat(UUID.fromString(original.path("id").asText())).isNotNull();
        assertThat(original.path("sku").asText()).isEqualTo("DEMO-1");
        assertThat(original.path("quantity").asInt()).isEqualTo(6);

        assertReplay(original);
        assertError(reserve("first", "DEMO-1", 5), HttpStatus.CONFLICT, "KEY_REUSED");
        assertError(reserve("first", "OTHER-1", 6), HttpStatus.CONFLICT, "KEY_REUSED");
        assertReplay(original);
        assertError(reserve("over-remaining", "DEMO-1", 5), HttpStatus.CONFLICT, "INSUFFICIENT_STOCK");

        ResponseEntity<JsonNode> last = reserve("remaining", "DEMO-1", 4);
        assertThat(last.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(last.getBody().path("quantity").asInt()).isEqualTo(4);
    }

    private ResponseEntity<JsonNode> reserve(String key, String sku, int quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Idempotency-Key", key);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(Map.of("sku", sku, "quantity", quantity), headers);
        return client.postForEntity("/reservations", request, JsonNode.class);
    }

    private void assertReplay(JsonNode original) {
        ResponseEntity<JsonNode> replay = reserve("first", "DEMO-1", 6);
        assertThat(replay.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(replay.getBody()).isEqualTo(original);
    }

    private static void assertError(ResponseEntity<JsonNode> response, HttpStatus status, String code) {
        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().path("code").asText()).isEqualTo(code);
    }
}
