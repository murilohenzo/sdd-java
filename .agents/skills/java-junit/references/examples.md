# Exemplos JUnit 5

Os exemplos são adaptáveis. Confirme imports e versões no build antes de usá-los.

## Unidade pura

~~~java
class PriceCalculatorTest {

    private final PriceCalculator calculator = new PriceCalculator();

    @Test
    @DisplayName("AC-001: calcula o desconto para cliente elegível")
    void calculatesDiscountForEligibleCustomer() {
        Price price = new Price(new BigDecimal("100.00"));

        Price result = calculator.applyDiscount(price, CustomerType.PREMIUM);

        assertThat(result.amount()).isEqualByComparingTo("90.00");
    }
}
~~~

O Arrange, Act e Assert devem ser visualmente identificáveis. Se o cenário exigir muitos objetos, use uma fixture nomeada, sem esconder a regra testada.

## Exceção

~~~java
@Test
@DisplayName("rejeita pedido sem itens")
void rejectsOrderWithoutItems() {
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> orderService.create(List.of())
    );

    assertThat(error).hasMessage("O pedido deve possuir ao menos um item");
}
~~~

Valide a mensagem somente se ela fizer parte do contrato; caso contrário, valide o tipo e os dados relevantes da exceção.

## Parametrização

~~~java
@ParameterizedTest(name = "[{index}] valor {0} é inválido")
@CsvSource({
    "0",
    "-1"
})
@DisplayName("rejeita valores que não podem ser positivos")
void rejectsNonPositiveValues(int value) {
    assertThatThrownBy(() -> validator.validate(value))
        .isInstanceOf(IllegalArgumentException.class);
}
~~~

Use MethodSource quando os dados forem objetos ou quando cada caso precisar de um nome e uma expectativa explícita.

## Mockito

~~~java
@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock UserRepository repository;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks RegisterUserService service;

    @Test
    @DisplayName("salva usuário com senha codificada")
    void savesUserWithEncodedPassword() {
        when(passwordEncoder.encode("segredo")).thenReturn("hash");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.register("ana@example.com", "segredo");

        verify(repository).save(argThat(user -> user.password().equals("hash")));
    }
}
~~~

Prefira verificar o resultado e os efeitos essenciais. Não verifique todas as chamadas internas apenas por hábito.

## Relógio controlado

~~~java
class ExpirationPolicyTest {

    private final Clock clock =
        Clock.fixed(Instant.parse("2026-01-10T12:00:00Z"), ZoneOffset.UTC);

    @Test
    @DisplayName("considera expirado o token após o instante limite")
    void expiresTokenAfterDeadline() {
        ExpirationPolicy policy = new ExpirationPolicy(clock);

        assertThat(policy.isExpired(Instant.parse("2026-01-10T11:59:59Z"))).isFalse();
        assertThat(policy.isExpired(Instant.parse("2026-01-10T12:00:00Z"))).isTrue();
    }
}
~~~

