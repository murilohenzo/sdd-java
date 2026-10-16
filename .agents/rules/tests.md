---
name: tests
description: Princípios comuns e roteamento para escolher testes Java ou Spring conforme comportamento, dependências e versões reais.
---

# Regras de testes

## Roteamento obrigatório

Use esta regra para decisões compartilhadas. Carregue apenas a skill específica ao contexto:

- [java-junit](../skills/java-junit/SKILL.md): lógica Java sem contexto Spring, com framework e bibliotecas confirmados no build;
- [springboot-test](../skills/springboot-test/SKILL.md): contexto Spring Boot, controllers, MVC/WebFlux, persistência, configuração de beans, segurança, mensageria, Testcontainers ou testes end-to-end.

Se o teste combina lógica pura e Spring, mantenha a lógica pura em testes JUnit e use a skill Spring Boot somente para a integração que realmente precisa do contexto. Antes de sugerir uma anotação ou API, verifique o pom.xml ou build.gradle do módulo e a versão efetiva das dependências.

## Regras comuns

Todo comportamento relevante deve ter validação proporcional ao risco. Use a meta de cobertura configurada no build ou no perfil local; quando não houver meta, não invente um percentual. Cobertura não substitui cenários críticos, limites e falhas esperadas.

Todo teste deve ser:

- rápido o suficiente para feedback frequente;
- independente de outros testes e da ordem de execução;
- determinístico e repetível;
- autovalidável, com assertions que comprovem o comportamento esperado.

Estruture o cenário como Given/When/Then ou Arrange/Act/Assert. Separe claramente preparação, ação e verificação. Prefira uma razão principal de falha por teste, nomes que expressem comportamento e dados de teste mínimos.

## Estratégia de níveis

Escolha a distribuição da suíte pelo comportamento e risco do módulo, sem impor proporções. Use o menor nível capaz de validar o comportamento:

- lógica pura: JUnit, sem Spring;
- colaboração entre classes: JUnit com doubles apropriados;
- contrato HTTP e serialização: slice web do framework;
- persistência real e migrations: slice de dados com banco controlado;
- fluxo completo: contexto da aplicação e dependências reais isoladas.

Não use um contexto completo para testar uma classe que pode ser testada sem framework. Não use mock para validar justamente a integração que o teste deveria comprovar.

## Fixtures e dados

Mantenha fixtures no código de teste, preferencialmente em src/test/java/.../testsupport/. Use builders ou factories nomeadas quando reduzirem ruído e deixarem a intenção evidente. Não compartilhe estado mutável entre testes e não coloque código de produção em fixtures.

## Qualidade e manutenção

- Teste resultados observáveis e regras de negócio, não detalhes internos sem valor comportamental.
- Cubra caminhos felizes, limites, entradas inválidas, falhas esperadas e efeitos relevantes.
- Use parametrização para variações da mesma decisão, evitando copiar testes quase iguais.
- Controle tempo, aleatoriedade, UUIDs e chamadas externas por abstrações ou doubles quando isso for necessário para determinismo.
- Não desabilite, ignore ou afrouxe assertions apenas para fazer a suíte passar.
- Ao corrigir um bug, adicione primeiro um teste que reproduza o comportamento incorreto.
- Trate testes instáveis como defeito de qualidade, investigando concorrência, tempo, estado compartilhado, rede e ordem de execução.

## Entrega

Ao criar ou revisar testes, informe o nível do teste, dependências externas, comando de execução e qualquer limitação de cobertura. Não adicione bibliotecas ou APIs de teste sem confirmar compatibilidade e necessidade no módulo.
