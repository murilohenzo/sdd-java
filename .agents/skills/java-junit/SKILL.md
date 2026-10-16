---
name: java-junit
description: Cria e revisa testes unitários Java sem iniciar Spring, usando o framework e as bibliotecas presentes no build. Use para domínio, casos de uso puros, validadores, mappers e serviços isoláveis.
---

# Java e JUnit

## Quando usar

Use esta skill quando a classe puder ser testada sem Spring, banco, rede, broker ou ApplicationContext. Para controllers, repositories, wiring e integrações Spring, use [springboot-test](../springboot-test/SKILL.md).

## Procedimento

1. Leia o framework de teste, a versão efetiva e as bibliotecas de doubles/assertions no build do módulo.
2. Entenda o comportamento público e suas entradas, saídas, exceções e efeitos.
3. Separe dependências determinísticas de integrações externas.
4. Escreva o menor teste isolado que falha se o comportamento mudar.
5. Use parametrização para uma mesma decisão com vários dados.
6. Execute o teste do módulo e revise falsos positivos, estado compartilhado e cobertura de cenários.

## Regras

- Use JUnit 5 quando ele estiver configurado; preserve outro framework existente quando a migração não fizer parte do escopo.
- Use `@DisplayName` somente quando essa for a convenção local ou quando ele melhorar materialmente a leitura do relatório.
- Estruture cada caso como Arrange/Act/Assert ou Given/When/Then.
- Teste comportamento observável, não implementação privada ou chamadas sem relevância.
- Prefira um motivo de falha por teste e nomes que expressem a regra de negócio.
- Quando Mockito estiver configurado, use-o apenas para colaboradores; não transforme o teste em uma cópia da implementação verificando cada chamada.
- Use assertThrows para exceções e valide tipo, mensagem segura ou causa somente quando isso fizer parte do contrato.
- Controle Clock, aleatoriedade, identificadores e tempo de espera por abstrações injetáveis quando afetarem o resultado.
- Evite sleeps, rede, filesystem real e banco em testes unitários.
- Não compartilhe estado mutável entre testes. O ciclo padrão por método do JUnit deve continuar suficiente.
- Use fixtures pequenas; extraia builders/factories apenas quando reduzirem repetição sem esconder o cenário.
- Não aumente a cobertura apenas com assertions irrelevantes; priorize limites, falhas e fluxos críticos.
- Use tags de requisito ou caso de aceite quando o projeto já as utiliza.

## Recursos

- Para exemplos de testes, parametrização, Mockito e controle de tempo, leia [examples.md](references/examples.md).
- Para critérios de qualidade e APIs oficiais, leia [best-practices.md](references/best-practices.md).

