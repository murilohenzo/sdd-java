# Roteiro de análise arquitetural

## 1. Descoberta mínima

Comece por uma árvore de arquivos e pelos arquivos de entrada do build. Em seguida, localize:

- módulos e pontos de inicialização;
- APIs e contratos;
- persistência, migrations e ownership de dados;
- comunicação HTTP, mensageria, eventos e jobs;
- autenticação, autorização e secrets externos;
- testes, pipelines, containers e manifests;
- documentação de execução e decisões anteriores.

Não leia todo o repositório indiscriminadamente. Use busca direcionada por extensões, nomes de configuração, annotations, clients, listeners, repositories e comandos de build.

## 2. Classificação de evidências

Use quatro estados:

| Estado | Uso |
|---|---|
| `observado` | Está explícito no código, build, teste ou configuração consultada. |
| `inferido` | É uma conclusão razoável apoiada por mais de uma evidência, mas não declarada diretamente. |
| `não confirmado` | Foi mencionado ou sugerido, mas não foi possível verificar. |
| `recomendado` | Melhoria proposta, sem afirmar que já exista. |

Exemplo: uma URL em um `application-local.yml` é uma comunicação observada; concluir que ela é obrigatória em produção é apenas uma hipótese até encontrar deployment ou documentação que confirme.

## 3. Mapa de comunicação

Use uma linha por relação real:

| Origem | Destino | Canal/contrato | Sync | Dados sob ownership | Evidência | Risco/melhoria |
|---|---|---|---|---|---|---|
| `api-gateway` | `servico-contas` | HTTP, rota configurada | Sim | `servico-contas` | `application-local.yml` | Validar timeout |

Não trate banco compartilhado, biblioteca comum ou broker como se fossem comunicação direta de negócio. Registre o tipo de relação correto.

## 4. Avaliação arquitetural

Avalie cada fronteira com perguntas objetivas:

- O módulo tem uma responsabilidade compreensível?
- Quem é dono dos dados?
- A dependência é necessária ou apenas conveniente?
- O contrato é explícito e versionável?
- A falha, repetição, timeout e reprocessamento têm comportamento definido?
- A mudança em um módulo exige conhecimento interno de outro?
- O teste prova o comportamento ou somente a estrutura?

Priorize os problemas por impacto em integridade de dados, disponibilidade, segurança, evolução independente e custo de manutenção.

## 5. Greenfield

Quando não houver implementação suficiente, produza duas seções: `Arquitetura proposta` e `Premissas a validar`. Cada decisão deve mencionar alternativas, trade-offs e critério de revisão. Evite declarar portas, schemas ou dependências como existentes.

## 6. Brownfield

Registre o estado observado antes do estado desejado. Para cada melhoria, indique:

1. problema e evidência;
2. objetivo arquitetural;
3. escopo afetado;
4. risco e dependências;
5. sequência incremental;
6. teste ou sinal de conclusão.

