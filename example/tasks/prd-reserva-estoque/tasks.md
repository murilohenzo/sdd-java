# Tarefas — Reserva de estoque

- Estado — pronto.
- Revisão — 3.
- Atualizado em — 2026-11-20.
- Origem — [PRD revisão 2](prd.md) e [TechSpec revisão 3](techspec.md).

## Entregas

- [x] 1.0 — [API de reserva e replay](task_1.md) — concluida.
- [x] 2.0 — [Consistência concorrente](task_2.md) — concluida, depende de 1.0.
- [x] 3.0 — [Fronteiras hexagonais](task_3.md) — concluida, depende de 1.0 e 2.0.

## Mapa de cobertura

- RF-01 → CA-01 → TC-01 / TU-01 → 1.0.
- RF-02 → CA-02 / CA-03 → TC-01 / TU-01 → 1.0.
- RF-03 → CA-04 → TC-02 / TC-03 / TC-04 / TU-02 → 1.0.
- RNF-01 → CA-05 → TU-04 / TU-05 → 2.0.
- RNF-02 → CA-06 → MAN-01 → 1.0.

A tarefa 3.0 protege as entregas 1.0 e 2.0 com ARQ-01 e revalidação de CA-01 a CA-06.

O [QA](qa.md) registra as verificações do plano vigente e a reconciliação com o PRD.
