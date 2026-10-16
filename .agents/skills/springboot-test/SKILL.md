---
name: springboot-test
description: Cria e revisa testes de aplicações Spring Boot usando o menor contexto necessário, slices, MockMvc ou WebTestClient, persistência, segurança, mensageria e Testcontainers. Use quando o comportamento depende de beans, auto-configuração, HTTP ou infraestrutura real.
---

# Testes Spring Boot

## Quando usar

Use esta skill quando o teste precisar de ApplicationContext, auto-configuração, controllers, filtros, repositories, transações, mensageria ou serviços externos. Para lógica que pode rodar sem Spring, use [java-junit](../java-junit/SKILL.md).

## Verificação de compatibilidade

Antes de escolher uma anotação ou API:

1. leia o pom.xml ou build.gradle do módulo;
2. identifique a versão real do Spring Boot, Spring Framework, Spring MVC/WebFlux, Spring Data e Testcontainers;
3. confirme o framework de teste, as dependências e o tipo de aplicação;
4. verifique se o módulo usa MVC ou WebFlux e qual banco/mensageria realmente existe.

Não aplique recomendações de outra geração do Spring Boot, Spring Framework ou Java. Confirme também se anotações de teste foram introduzidas, depreciadas ou removidas na versão efetiva.

## Escolha do nível

- Use WebMvcTest para controller MVC, binding, validação, serialização, status e advice, com colaboradores substituídos.
- Use WebFluxTest para endpoints WebFlux e WebTestClient.
- Use DataJpaTest ou o slice equivalente para mapeamento, queries e transações JPA.
- Use SpringBootTest somente quando integração entre configuração, beans ou múltiplas camadas for o comportamento testado.
- Use RANDOM_PORT para verificar o fluxo HTTP real; não o use apenas para testar um método de controller.
- Use TestConfiguration e Import para adicionar somente beans auxiliares necessários ao slice.

Não transforme todo teste em SpringBootTest: contexto lento e mocks excessivos indicam nível inadequado.

## Regras

- Use `@DisplayName` quando essa for a convenção do projeto ou quando ele melhorar materialmente o relatório.
- Separe Arrange, Act e Assert; valide status, corpo, headers, eventos ou persistência observáveis.
- Substitua colaboradores externos no slice com a API compatível com a versão. Verifique se o projeto disponibiliza `@MockBean`, `@MockitoBean` ou outro mecanismo; não troque APIs apenas por modernização fora de escopo.
- Não use mocks para substituir a integração que o teste pretende validar.
- Em testes de banco, valide constraints, migrations, queries e mapeamentos relevantes; não dependa apenas do H2 se produção usa comportamento diferente de MySQL.
- Limpe dados e recursos entre testes. Não dependa da ordem nem de estado persistido por outro caso.
- Para Testcontainers, confirme Docker, dependências e suporte a ServiceConnection na versão real. Use a integração Spring Boot quando disponível; em versões antigas, use DynamicPropertySource ou a configuração equivalente.
- Escolha lifecycle de containers compatível com o cache do ApplicationContext. Evite container parado enquanto o contexto cacheado ainda pode ser reutilizado.
- Para MVC, use MockMvc; para WebFlux, use WebTestClient. Não misture APIs sem necessidade.
- Teste validação de entrada, autenticação/autorização e tratamento global de erros quando forem responsabilidade da borda HTTP.
- Não exponha credenciais reais, tokens ou dados sensíveis em propriedades e fixtures.
- Não use DirtiesContext como correção padrão para estado compartilhado; corrija o isolamento ou o lifecycle.
- Não adicione handlers, configurações ou dependências de uma tecnologia que o módulo não usa.

## Recursos

- Para exemplos de slices, HTTP, JPA, Testcontainers e compatibilidade de APIs, leia [examples.md](references/examples.md).
- Para estratégia e boas práticas de contexto, leia [best-practices.md](references/best-practices.md).

