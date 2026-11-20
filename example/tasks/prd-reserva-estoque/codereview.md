# Revisão de código — Reserva de estoque

- Data — 2026-11-20.
- Base — implementação greenfield em `example/` no estado local de 2026-11-20.
- Referências — [PRD 2](prd.md), [TechSpec 2](techspec.md), [plano 2](tasks.md) e [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md).
- Veredito — **APROVADO**.

## Achados e conformidade

Nenhum defeito bloqueador identificado. A [borda HTTP](../../src/main/java/br/example/reservation/ReservationController.java) valida os tipos JSON e a chave antes de chamar o serviço. Ela traduz rejeições pelos códigos declarados. O [serviço](../../src/main/java/br/example/reservation/ReservationService.java) verifica primeiro a confirmação anterior e só grava a chave no fluxo que debita saldo. Um único bloqueio protege as duas estruturas de estado. A [configuração](../../src/main/resources/application.properties) usa loopback, conforme o ADR.

## Validação

`mvn -B -ntp verify` em `example/` passou com Java 21.0.12, Maven 3.9.16 e Spring Boot 3.5.10. Foram 25 testes sem falha, erro ou skip. O JAR foi empacotado e validado conforme [MAN-01](qa.md#man-01--escuta-local-e-reinício).

O escopo aprovado é o processo local com estado descartável definido pelo PRD. A validação HTTP usou loopback em portas dinâmicas.
