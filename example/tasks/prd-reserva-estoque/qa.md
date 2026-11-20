# QA — Reserva de estoque

- Data — 2026-11-20.
- Base — implementação greenfield em `example/`, Java 21.0.12, Maven 3.9.16 e Spring Boot 3.5.10.
- Referências — [PRD 2](prd.md), [TechSpec 2](techspec.md), [plano 2](tasks.md) e [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md).
- Veredito — **APROVADO**.

## Aceite verificado

Comando da suíte — `mvn -B -ntp verify` no diretório `example/`. Maven concluiu com `BUILD SUCCESS` e 25 testes, zero falhas, erros ou skips. O Surefire registrou oito casos no [serviço](../../src/test/java/br/example/reservation/ReservationServiceTest.java), dezesseis no [slice MVC](../../src/test/java/br/example/reservation/ReservationValidationTest.java) e uma jornada [HTTP em porta aleatória](../../src/test/java/br/example/reservation/ReservationHttpTest.java). Os relatórios são gerados em `example/target/surefire-reports/`.

| Requisito e aceite | Verificação → tarefa | Resultado observado |
| --- | --- | --- |
| RF-01 / CA-01 | TC-01, TU-01 → 1.0 | **PASSOU** · 201 com UUID e seis unidades. A rejeição de cinco seguida da confirmação de quatro prova o saldo exato. |
| RF-02 / CA-02 | TC-01, TU-01 → 1.0 | **PASSOU** · 200 devolveu a confirmação original. A fixture de replay manteve o saldo em quatro. |
| RF-02 / CA-03 | TC-01, TU-01 → 1.0 | **PASSOU** · mudanças isoladas de SKU e quantidade retornaram 409 `KEY_REUSED`. Replay posterior manteve a reserva, e o saldo continuou em quatro. |
| RF-03 / CA-04 | TC-02, TC-03, TC-04, TU-02 → 1.0 | **PASSOU** · 400 `INVALID_REQUEST` sem chamar o serviço, 404 `SKU_NOT_FOUND` e 409 `INSUFFICIENT_STOCK`. Cada rejeição de domínio preservou as dez unidades em fixture própria. |
| RNF-01 / CA-05 | TU-04, TU-05 → 2.0 | **PASSOU** · duas chaves disputando seis unidades geraram uma confirmação e uma rejeição. Com a mesma chave houve uma confirmação e um replay idêntico. Ambas as disputas deixaram quatro unidades. |
| RNF-02 / CA-06 | MAN-01 → 1.0 | **PASSOU** · escuta em loopback nas duas inicializações. A mesma chave confirmou dez unidades novamente com novo UUID após reiniciar. |

## MAN-01 — Escuta local e reinício

1. Em `example/`, inicie `java -jar target/stock-reservation-0.1.0-SNAPSHOT.jar --server.port=0`. Anote a porta e o PID exibidos no log. Use `netstat -ano -p tcp` para conferir o endereço local associado a esse PID e porta.
2. Envie `POST /reservations` em `127.0.0.1`, com `Idempotency-Key: restart-proof` e `{"sku":"DEMO-1","quantity":10}`. Espere 201. Repita para obter 200 com o mesmo corpo. Peça uma unidade com outra chave e espere 409 `INSUFFICIENT_STOCK`.
3. Encerre a aplicação, inicie novamente e repita as chamadas. Espere a mesma sequência de status e um novo identificador de confirmação.

Execução observada — o PID 35328 escutou em `127.0.0.1:53915` e retornou 201/200/409, com id `c4da2c57-d305-4e92-a10c-0e198e78ed93`. Após encerrar, o PID 25636 escutou em `127.0.0.1:53919` e retornou 201/200/409, com id `b07d9930-03dd-4891-bee3-133e6e1f03e9`. Os dois processos eram da verificação e foram encerrados. SHA-256 do JAR usado — `3141dc3e95d2df340e338680c94cb9315f69f9c4ca3d3c3a18f71b73d9bae9ff`.

## Fechamento

Todos os seis CA da revisão 2 estão ligados a verificações, tarefas concluídas e evidência real. A execução inicial no sandbox teve bloqueio de loopback e foi refeita com autorização para conexões locais. O empacotamento completo ocorreu após baixar dependências do plugin Maven. O [review de código](codereview.md) aprovou a implementação no escopo de uma instância local. Os achados [Elephant-Goldfish](spec-review.md) foram convertidos em verificações antes do código e estão cobertos nos resultados acima.
