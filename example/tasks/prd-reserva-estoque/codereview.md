# Revisão de código — Reserva de estoque

- Data — 2026-11-20.
- Base — commit `7cbc5ae` (revisão 2) e alterações da tarefa 3.0 em `example/`.
- Referências — [PRD 2](prd.md), [TechSpec 3](techspec.md), [plano 3](tasks.md), [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md) e [ADR 0002](../../docs/architecture/adr/0002-ports-and-adapters.md).
- Veredito — **APROVADO**.

## Achados corrigidos

- **Fronteiras hexagonais.** A revisão 2 tinha um bean Spring que detinha as regras de negócio e um controller acoplado aos tipos da implementação. A revisão 3 usa uma porta de entrada e compõe as dependências em infraestrutura. O DTO HTTP pertence ao adaptador. [Caso de uso](../../src/main/java/br/example/reservation/core/application/ReserveStockService.java) · [Controller](../../src/main/java/br/example/reservation/adapters/inbound/http/ReservationController.java) · [Composição](../../src/main/java/br/example/reservation/infrastructure/ReservationConfiguration.java)
- **Responsabilidade única.** Na revisão 2, alterações de regra, mecanismo de armazenamento, sincronização ou carga inicial afetavam o serviço. Na revisão 3, saldo e idempotência ficam juntos no agregado porque compartilham uma invariante. A sincronização pertence ao adaptador. A carga inicial pertence à composição. Na borda, a validação da requisição e a tradução de erros têm componentes próprios. [Agregado](../../src/main/java/br/example/reservation/core/domain/ReservationLedger.java) · [Adaptador em memória](../../src/main/java/br/example/reservation/adapters/outbound/memory/InMemoryReservationStore.java) · [Validação HTTP](../../src/main/java/br/example/reservation/adapters/inbound/http/ReservationHttpRequest.java) · [Erros HTTP](../../src/main/java/br/example/reservation/adapters/inbound/http/ReservationHttpErrors.java)

## Validação

`mvn -B -ntp -o verify` em `example/` passou com 32 testes, incluindo seis checks do ArchUnit 1.5.1. Uma classe compilada temporariamente no domínio com `@Service` produziu a falha de arquitetura esperada. Após removê-la, todos os checks passaram. [Regras compiladas](../../src/test/java/br/example/reservation/ArchitectureRulesTest.java) · [QA e MAN-01](qa.md)

O escopo aprovado é o processo local com estado descartável. Os checks cobrem dependências e localização. A análise de responsabilidade única foi feita por inspeção das causas de mudança. A revisão 2 registrava o aceite funcional antes de o perfil hexagonal ser adotado.
