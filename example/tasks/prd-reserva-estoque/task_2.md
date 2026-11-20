# Tarefa 2.0 — Consistência concorrente

- Estado — concluida.
- Dependência — 1.0.
- Base — [PRD revisão 2](prd.md), [TechSpec revisão 2](techspec.md), [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md).
- Critério — CA-05.

## Entrega e conclusão

- [x] Verificar que solicitações concorrentes com chaves distintas disputam o saldo sem ultrapassá-lo.
- [x] Verificar replay concorrente com a mesma chave sem segundo débito.

## Execução

Na revisão 2, TU-04 e TU-05 passaram em `mvn -B -ntp verify` com diretório `example/`. A [tarefa 3.0](task_3.md) repetiu as disputas para provar o comportamento do caso de uso com o adaptador em memória. Ambas deixaram quatro unidades remanescentes. [Evidência vigente de QA](qa.md#aceite-verificado)
