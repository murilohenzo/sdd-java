# Boas práticas para Java e JUnit 5

## Decisões de teste

- Comece pelo contrato público da unidade e pelos riscos do comportamento.
- Prefira testes determinísticos e independentes; um teste não deve depender de outro ou da ordem de execução.
- Mantenha o teste rápido e sem infraestrutura externa. Integrações pertencem a outro nível da pirâmide.
- Use uma assertion que explique o resultado esperado; cobertura de linha sem verificação de comportamento não é evidência suficiente.
- Escreva testes que falhem por uma causa compreensível e evite múltiplas regras não relacionadas no mesmo caso.

## JUnit 5

- O ciclo padrão cria uma instância por método, o que ajuda a evitar estado compartilhado; só use lifecycle por classe quando a economia ou o recurso compartilhado forem intencionais e resetados.
- Use parametrização para tabelas de decisão e limites. Escolha a fonte mais legível: CsvSource para valores simples e MethodSource para objetos.
- Mantenha BeforeEach pequeno. Se o setup dominar o teste, extraia uma fixture explícita ou reveja o desenho da unidade.
- Use extensões JUnit para lifecycle e recursos, não inicialização manual espalhada.
- Reserve testes dinâmicos ou repetidos para casos que realmente exigem geração ou repetição; não use para esconder cenários difíceis de nomear.

## Mockito e doubles

- Mocke colaboradores instáveis, lentos ou fora do limite da unidade; não mocke o objeto sob teste.
- Prefira stubs mínimos e verifique somente efeitos que fazem parte do contrato.
- Muitos mocks, stubs não usados ou verificações excessivas indicam acoplamento e podem justificar refatoração.
- Para valores, tempo e IDs, prefira dependências controláveis a mocks de métodos estáticos.
- Não use mock para reproduzir o comportamento da implementação real; isso cria testes que passam mesmo quando a integração está quebrada.

## Dados, tempo e concorrência

- Use dados pequenos e nomes de fixture que comuniquem intenção.
- Injete Clock quando tempo fizer parte da decisão; não dependa de Instant.now diretamente no teste.
- Evite sleep para sincronização. Quando concorrência for o comportamento testado, use mecanismos explícitos de sincronização e timeout proporcional.
- Não use aleatoriedade sem seed ou sem validar a propriedade esperada.
- Limpe recursos criados pelo teste e prefira APIs de lifecycle do framework.

## Referências oficiais

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JUnit 5 Assertions](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html)
- [Mockito JUnit Jupiter](https://javadoc.io/doc/org.mockito/mockito-junit-jupiter/latest/org/mockito/junit/jupiter/MockitoExtension.html)

