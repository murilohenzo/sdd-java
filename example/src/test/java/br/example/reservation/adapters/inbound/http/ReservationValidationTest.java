package br.example.reservation.adapters.inbound.http;

import java.util.stream.Stream;

import br.example.reservation.core.application.port.inbound.ReserveStock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationValidationTest {

    private static final String VALID_BODY = """
            {"sku":"DEMO-1","quantity":6}
            """;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ReserveStock reserveStock;

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidRequests")
    void rejectsInvalidRequestsBeforeTheyReachStock(String scenario, String key, String body) throws Exception {
        MockHttpServletRequestBuilder request = post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        if (key != null) {
            request.header("Idempotency-Key", key);
        }

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        verifyNoInteractions(reserveStock);
    }

    private static Stream<Arguments> invalidRequests() {
        return Stream.of(
                Arguments.of("missing key", null, VALID_BODY),
                Arguments.of("invalid key", "invalid key", VALID_BODY),
                Arguments.of("empty key", "", VALID_BODY),
                Arguments.of("missing SKU", "request", "{\"quantity\":6}"),
                Arguments.of("SKU outside the format", "request", "{\"sku\":\"demO-1\",\"quantity\":6}"),
                Arguments.of("non-string SKU", "request", "{\"sku\":123,\"quantity\":6}"),
                Arguments.of("missing quantity", "request", "{\"sku\":\"DEMO-1\"}"),
                Arguments.of("null quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":null}"),
                Arguments.of("zero quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":0}"),
                Arguments.of("negative quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":-1}"),
                Arguments.of("quoted quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":\"6\"}"),
                Arguments.of("decimal quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":6.0}"),
                Arguments.of("exponential quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":6e0}"),
                Arguments.of("oversized quantity", "request", "{\"sku\":\"DEMO-1\",\"quantity\":2147483648}"),
                Arguments.of("broken JSON", "request", "{"),
                Arguments.of("trailing JSON value", "request", VALID_BODY + "{}"));
    }
}
