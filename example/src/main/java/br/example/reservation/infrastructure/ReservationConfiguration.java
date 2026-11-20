package br.example.reservation.infrastructure;

import br.example.reservation.adapters.outbound.memory.InMemoryReservationStore;
import br.example.reservation.core.application.ReserveStockService;
import br.example.reservation.core.application.port.inbound.ReserveStock;
import br.example.reservation.core.application.port.outbound.ReservationStore;
import br.example.reservation.core.domain.ReservationLedger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ReservationConfiguration {

    @Bean
    ReservationStore reservationStore() {
        return new InMemoryReservationStore(new ReservationLedger("DEMO-1", 10));
    }

    @Bean
    ReserveStock reserveStock(ReservationStore store) {
        return new ReserveStockService(store);
    }
}
