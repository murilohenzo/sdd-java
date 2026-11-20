# Tarefa 3.0 — Fronteiras hexagonais

- Estado — concluida.
- Dependências — 1.0 e 2.0.
- Base — [PRD revisão 2](prd.md), [TechSpec revisão 3](techspec.md), [ADR 0002](../../docs/architecture/adr/0002-ports-and-adapters.md).

## Entrega e conclusão

- [x] Isolar invariantes de domínio e caso de uso de Spring, HTTP e armazenamento.
- [x] Implementar adaptadores, composição e DTOs com responsabilidades distintas.
- [x] Executar ARQ-01 e revalidar CA-01 a CA-06 no código revisado.

## Execução

**PASSOU** — `mvn -B -ntp -o verify` em `example/` executou 32 testes, incluindo seis regras do ArchUnit. A sonda de domínio com `@Service` foi rejeitada e removida antes da execução verde. [MAN-01](qa.md#man-01--escuta-local-e-reinício) passou com o JAR revisado. [Revisão de responsabilidades](codereview.md)
