# QA — Reserva de estoque

- Data — 2026-11-20.
- Base — implementação da tarefa 3.0 em `example/`, Java 21.0.12, Maven 3.9.16 e Spring Boot 3.5.10.
- Referências — [PRD 2](prd.md), [TechSpec 3](techspec.md), [plano 3](tasks.md), [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md) e [ADR 0002](../../docs/architecture/adr/0002-ports-and-adapters.md).
- Veredito — **APROVADO**.

## Aceite verificado

Comando da suíte — `mvn -B -ntp -o verify` no diretório `example/`. Maven concluiu com `BUILD SUCCESS` e 32 testes, zero falhas, erros ou skips. O Surefire registrou nove casos no [caso de uso e adaptador em memória](../../src/test/java/br/example/reservation/ReserveStockServiceTest.java), dezesseis no [slice MVC](../../src/test/java/br/example/reservation/adapters/inbound/http/ReservationValidationTest.java), uma jornada [HTTP em porta aleatória](../../src/test/java/br/example/reservation/ReservationHttpTest.java) e seis regras [ArchUnit](../../src/test/java/br/example/reservation/ArchitectureRulesTest.java). Os relatórios são gerados em `example/target/surefire-reports/`.

| Requisito e aceite | Verificação → tarefa | Resultado observado |
| --- | --- | --- |
| RF-01 / CA-01 | TC-01, TU-01 → 1.0 | **PASSOU** · 201 com UUID e seis unidades. A rejeição de cinco seguida da confirmação de quatro prova o saldo exato. |
| RF-02 / CA-02 | TC-01, TU-01 → 1.0 | **PASSOU** · 200 devolveu a confirmação original. A fixture de replay manteve o saldo em quatro. |
| RF-02 / CA-03 | TC-01, TU-01 → 1.0 | **PASSOU** · mudanças isoladas de SKU e quantidade retornaram 409 `KEY_REUSED`. Replay posterior manteve a reserva, e o saldo continuou em quatro. |
| RF-03 / CA-04 | TC-02, TC-03, TC-04, TU-02 → 1.0 | **PASSOU** · 400 `INVALID_REQUEST` sem chamar a porta de entrada, 404 `SKU_NOT_FOUND` e 409 `INSUFFICIENT_STOCK`. Cada rejeição de domínio preservou as dez unidades em fixture própria. |
| RNF-01 / CA-05 | TU-04, TU-05 → 2.0 | **PASSOU** · duas chaves disputando seis unidades geraram uma confirmação e uma rejeição. Com a mesma chave houve uma confirmação e um replay idêntico. Ambas as disputas deixaram quatro unidades. |
| RNF-02 / CA-06 | MAN-01 → 1.0 | **PASSOU** · escuta em loopback nas duas inicializações. A mesma chave confirmou dez unidades novamente com novo UUID após reiniciar. |

## MAN-01 — Escuta local e reinício

1. Em `example/`, inicie `java -jar target/stock-reservation-0.1.0-SNAPSHOT.jar --server.port=0`. Anote a porta e o PID exibidos no log. Use `netstat -ano -p tcp` para conferir o endereço local associado a esse PID e porta.
2. Envie `POST /reservations` em `127.0.0.1`, com `Idempotency-Key: restart-proof` e `{"sku":"DEMO-1","quantity":10}`. Espere 201. Repita para obter 200 com o mesmo corpo. Peça uma unidade com outra chave e espere 409 `INSUFFICIENT_STOCK`.
3. Encerre a aplicação, inicie novamente e repita as chamadas. Espere a mesma sequência de status e um novo identificador de confirmação.

Execução observada — o PID 28736 escutou em `127.0.0.1:53852` e retornou 201/200/409, com id `9d7740d6-ec59-4261-9774-3205c852884f`. Após encerrar, o PID 28832 escutou em `127.0.0.1:53856` e retornou 201/200/409, com id `c04b8266-b2e0-4d1d-a29f-c6875e2eea9e`. Os dois processos eram da verificação e foram encerrados. SHA-256 do JAR usado — `ff8ab4f2c451c1cdffe3f621cfbeff7a6669b609de4891bae9fae08e50807a96`.

## Verificação arquitetural

ARQ-01 passou na [tarefa 3.0](task_3.md). O teste negativo isolado compilou em `target/classes` uma classe temporária de domínio anotada com `@Service` e confirmou a falha esperada em `ArchitectureRulesTest`. Após a remoção da sonda, o build passou e empacotou o JAR validado em MAN-01.

## Fechamento

Todos os seis CA do PRD 2 foram revalidados no desenho hexagonal da TechSpec 3. A suíte e o JAR usaram portas locais dinâmicas. O [review de código](codereview.md) aprovou também as fronteiras e a separação das responsabilidades. Os achados [Elephant-Goldfish](spec-review.md) do piloto inicial continuam cobertos no aceite.
