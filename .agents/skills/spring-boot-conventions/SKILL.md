---
name: spring-boot-conventions
description: Orienta criação e revisão de código Spring Boot e Spring Framework conforme versões, dependências e arquitetura reais. Use em beans, APIs, serviços, DTOs, clientes, segurança, erros, transações e integrações.
---

# Spring Boot Conventions

Esta skill fornece critérios version-aware e usa referências por assunto para evitar carregar contexto desnecessário.

## Fluxo obrigatório

1. Leia o `pom.xml`, `build.gradle` ou `build.gradle.kts` do módulo antes de sugerir APIs.
2. Confirme Spring Boot, Spring Framework, Spring Security, Java e dependências presentes.
3. Verifique se o módulo usa Spring MVC, WebFlux ou outro modelo.
4. Leia em [conventions.md](references/conventions.md) somente as seções relacionadas à tarefa.
5. Consulte [examples.md](references/examples.md) somente quando precisar de um exemplo correspondente.
6. Consulte [additional-best-practices.md](references/additional-best-practices.md) para configuração externa, Actuator, observabilidade, segurança, cache, async, scheduling, eventos ou ciclo de vida.
7. Diferencie regra obrigatória, preferência e recomendação dependente do contexto.
8. Execute os testes relevantes quando houver alteração de código.

## Compatibilidade e contexto local

Leia o perfil local indicado por `AGENTS.md`, quando existir. Não assuma versão de Java/Spring, arquitetura, Lombok ou outra biblioteca. Não adicione dependências apenas para usar um exemplo e prefira injeção por construtor independentemente de Lombok.

## Critérios de decisão

- Use a versão e as dependências reais do módulo como fonte de verdade.
- Não recomende uma API depreciada, removida ou ausente sem declarar a limitação e uma alternativa compatível.
- Não misture MVC e WebFlux sem justificar a fronteira.
- Não mova classes entre camadas ou módulos sem respeitar [architecture.md](../../rules/architecture.md) e as decisões locais.
- Não transforme uma recomendação contextual em proibição universal.
- Mantenha os exemplos em português brasileiro, com nomes de domínio e mensagens seguras.
- Para testes, siga [tests.md](../../rules/tests.md).

## Referências oficiais

- Spring Boot: https://docs.spring.io/spring-boot/reference/
- Spring Framework: https://docs.spring.io/spring-framework/reference/
- Spring Security: https://docs.spring.io/spring-security/reference/
- Para as 15 práticas adicionais e seus links oficiais, leia [additional-best-practices.md](references/additional-best-practices.md).

