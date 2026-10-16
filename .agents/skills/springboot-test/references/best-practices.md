# Boas práticas para testes Spring Boot

## Seleção de contexto

- Comece pelo menor contexto que prova o comportamento. Contextos completos devem ser exceção justificada.
- Slices reduzem tempo e acoplamento, mas não comprovam a integração de componentes que ficaram fora do slice.
- Se um slice exige muitos imports e mocks, avalie se ele ainda representa o comportamento pretendido ou se a unidade deve ser testada sem Spring.
- Use a classe de aplicação principal como configuração apenas quando ela realmente representar o módulo testado.
- Não reutilize contexto com propriedades ou beans mutáveis de forma que um teste contamine outro.

## Web

- Teste contrato HTTP: método, rota, status, headers, content type, serialização, validação e envelope de erro.
- Separe teste do controller de teste do caso de uso. O primeiro verifica adaptação HTTP; o segundo verifica regra de negócio.
- Teste segurança na borda com usuários, authorities e tokens de teste compatíveis com o mecanismo configurado.
- Não acople assertions a campos internos ou logs.
- Para WebFlux, use WebTestClient e aguarde fluxos reativos corretamente; não bloqueie a aplicação apenas para facilitar a assertion.

## Persistência

- Teste queries, constraints, relacionamentos, conversões e comportamento transacional relevante.
- O banco em teste deve revelar erros importantes do banco de produção. H2 pode ser insuficiente para semântica de MySQL, PostgreSQL ou recursos específicos.
- Use migrations reais quando o objetivo for validar o schema final.
- Limpe dados por transação, fixture isolada ou container descartável conforme o nível do teste; não dependa de rollback quando o comportamento testado inclui commit, eventos ou transação assíncrona.

## Containers e serviços externos

- Use imagens fixadas por versão, não latest.
- Verifique que Docker está disponível no ambiente que executa a suíte.
- Escolha lifecycle estático ou por método conscientemente. O lifecycle deve ser compatível com o cache de contexto do Spring.
- Prefira ServiceConnection quando suportado e quando a integração automática representar o serviço real. Use DynamicPropertySource como alternativa compatível com versões anteriores.
- Não use containers para um teste que só precisa de um mock. Não use mock para ocultar uma falha de integração que deve ser detectada.

## Configuração e diagnóstico

- Mantenha propriedades de teste explícitas e sem segredos.
- Evite @DirtiesContext; use-o apenas quando a alteração global de contexto for inevitável e documentada.
- Quando um contexto falhar, investigue o bean ou auto-configuração ausente antes de adicionar imports indiscriminadamente.
- Registre o motivo de um teste de integração, sua dependência externa e seu comando de execução.

## Referências oficiais

- [Spring Boot Testing](https://docs.spring.io/spring-boot/reference/testing/)
- [Testing Spring Boot Applications](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html)
- [Spring Boot Testcontainers](https://docs.spring.io/spring-boot/reference/testing/testcontainers.html)
- [Testcontainers JUnit 5](https://java.testcontainers.org/test_framework_integration/junit_5/)

