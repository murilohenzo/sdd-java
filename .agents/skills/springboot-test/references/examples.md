# Exemplos de testes Spring Boot

Confirme a versão real antes de copiar as anotações. Os exemplos usam uma aplicação MVC; para WebFlux, substitua o cliente pelo fluxo compatível.

## Controller MVC

~~~java
@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CheckoutService service;

    @Test
    @DisplayName("retorna 404 quando o pedido não existe")
    void returnsNotFoundWhenOrderDoesNotExist() throws Exception {
        when(service.findById("missing"))
            .thenThrow(new OrderNotFoundException());

        mvc.perform(get("/orders/{id}", "missing"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Pedido não encontrado"));
    }
}
~~~

Em Spring Boot 4, confirme se MockBean foi substituído pela API disponível no projeto. Não troque a anotação apenas por recomendação de outro projeto.

## Entrada inválida

~~~java
@Test
@DisplayName("retorna 400 quando o código não é informado")
void returnsBadRequestWhenCodeIsMissing() throws Exception {
    mvc.perform(post("/gift-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
}
~~~

Teste a resposta pública e segura; não exija stack trace ou mensagem interna.

## Persistência

~~~java
@DataJpaTest
class GiftCardRepositoryTest {

    @Autowired
    GiftCardRepository repository;

    @Test
    @DisplayName("persiste e recupera o saldo do gift card")
    void persistsAndReloadsBalance() {
        GiftCard saved = repository.save(GiftCard.create("ABC", new BigDecimal("100.00")));

        GiftCard loaded = repository.findById(saved.getId()).orElseThrow();

        assertThat(loaded.getBalance()).isEqualByComparingTo("100.00");
    }
}
~~~

Se produção usa MySQL e o comportamento testado depende do dialeto, execute contra MySQL real em container ou documente a limitação do banco substituto.

## Testcontainers com ServiceConnection

~~~java
@Testcontainers
@SpringBootTest
class UserIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql =
        new MySQLContainer<>("mysql:8.0");

    @Test
    @DisplayName("cria usuário usando o banco integrado")
    void createsUserUsingIntegratedDatabase() {
        // execute o fluxo público do caso de uso
    }
}
~~~

Use este padrão somente quando a versão do Spring Boot e as dependências incluírem ServiceConnection. Se o módulo usa GenericContainer ou uma versão anterior, configure as propriedades por mecanismo compatível e não invente connection details.

## Contexto mínimo

~~~java
@TestConfiguration(proxyBeanMethods = false)
class TestClockConfiguration {

    @Bean
    Clock testClock() {
        return Clock.fixed(Instant.parse("2026-01-10T12:00:00Z"), ZoneOffset.UTC);
    }
}

@WebMvcTest(OrderController.class)
@Import(TestClockConfiguration.class)
class OrderControllerTest {
}
~~~

Prefira importar uma configuração de teste pequena a carregar a aplicação inteira.

