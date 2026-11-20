# TechSpec — Reserva de estoque

- Estado — pronto.
- Revisão — 3.
- Atualizado em — 2026-11-20.
- Origem — [PRD revisão 2](prd.md), [blueprint](blueprint.md), [ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md) e [ADR 0002](../../docs/architecture/adr/0002-ports-and-adapters.md).

## Contexto e componentes

O componente começou greenfield e usa Java 21, Spring Boot 3.5.10, Maven e Spring MVC. A revisão 3 aplica o perfil hexagonal ao pacote `br.example.reservation`.

- `ReservationLedger` aplica as regras de saldo e idempotência. Seu estado representa uma única instância do estoque.
- `ReserveStockService` implementa `ReserveStock` e chama `ReservationStore` para aplicar a operação com acesso exclusivo.
- `InMemoryReservationStore` controla a sincronização. `ReservationConfiguration` compõe o caso de uso, o armazenamento e a carga inicial.
- `ReservationController` recebe a requisição e entrega o DTO HTTP de resposta. `ReservationHttpRequest` valida o protocolo e o JSON. `ReservationHttpErrors` traduz rejeições para status e códigos públicos.

## Contrato

`POST /reservations` recebe JSON com `sku` (string com maiúsculas ASCII, números ou hífen, 1 a 40 caracteres) e `quantity` (número inteiro positivo de 32 bits). Valores ausentes, nulos, strings numéricas, frações e notação exponencial são inválidos para `quantity`. O header obrigatório `Idempotency-Key` aceita 1 a 64 caracteres ASCII alfanuméricos, hífen ou sublinhado. O seed é `DEMO-1` com dez unidades. O identificador da reserva é um UUID.

| Situação | HTTP | Corpo |
| --- | --- | --- |
| Primeira confirmação | 201 | `id`, `sku`, `quantity` |
| Replay da mesma chave e dados | 200 | Mesma reserva |
| Chave confirmada com outros dados | 409 | `code: KEY_REUSED` |
| SKU válido desconhecido | 404 | `code: SKU_NOT_FOUND` |
| Quantidade superior ao saldo | 409 | `code: INSUFFICIENT_STOCK` |
| Chave ausente/inválida ou corpo JSON inválido | 400 | `code: INVALID_REQUEST` |

## Consistência

Valide a entrada HTTP antes de chamar a porta de entrada. Sob o bloqueio exclusivo do adaptador, o agregado consulta a chave confirmada, verifica existência do item e disponibilidade e então debita e grava a confirmação. Compare SKU e quantidade em valor no replay. Somente uma confirmação vincula a chave. Use uma instância de armazenamento por processo e confirme que as chaves são descartadas ao reiniciar. Configure a escuta em `127.0.0.1`.

## Verificações

Os casos automatizados são executáveis via `mvn -B -ntp verify` em `example/`. TC-01, TC-03 e TC-04 usam HTTP com porta aleatória. TC-02 usa slice MVC para provar que entradas inválidas são rejeitadas antes de chegar à porta de entrada. Os casos TU usam JUnit sem Spring.

| ID | CA | Cenário e resultado esperado |
| --- | --- | --- |
| TC-01 | CA-01, CA-02, CA-03 | Conferir 201 e corpo, 200 com corpo idêntico, 409 `KEY_REUSED` nos conflitos de SKU e quantidade e um novo replay do corpo original. Provar saldo exato verificando rejeição de cinco e confirmação de quatro. |
| TC-02 | CA-04 | Em casos independentes, conferir 400 `INVALID_REQUEST` e nenhuma chamada ao serviço para header ausente/inválido, SKU inválido, quantidade ausente/nula/inválida, formato de número vedado e JSON malformado. |
| TC-03 | CA-04 | SKU válido desconhecido produz 404 `SKU_NOT_FOUND`. |
| TC-04 | CA-04 | Quantidade acima do saldo produz 409 `INSUFFICIENT_STOCK`. |
| TU-01 | CA-01, CA-02, CA-03 | Isolar primeira confirmação, replay, conflito de SKU e conflito de quantidade. Em cada cenário provar que restam exatamente quatro unidades e que a reserva original continua recuperável quando houver conflito. |
| TU-02 | CA-04 | Em fixtures independentes, rejeitar item desconhecido ou excesso. Reutilizar a própria chave para reservar as dez unidades originais. |
| TU-04 | CA-05 | Disputar seis unidades com chaves distintas e observar uma confirmação, rejeição por saldo e quatro unidades restantes. |
| TU-05 | CA-05 | Disputar com a mesma chave e os mesmos dados e observar a mesma reserva e um único débito. |
| MAN-01 | CA-06 | Iniciar o JAR empacotado, verificar socket em `127.0.0.1` do processo e reservar dez unidades com uma chave inédita. Reiniciar o processo e repetir a requisição. Esperar 201 nos dois casos e identificadores distintos. |

**ARQ-01** — executar `ArchitectureRulesTest` para conferir isolamento do domínio e da aplicação, dependências dos adaptadores pelas portas e enquadramento das classes nas fronteiras adotadas. É um check obrigatório do ADR 0002 na tarefa 3.0.

## Entrega e operação

A primeira entrega disponibiliza a reserva, o contrato e os casos HTTP. A segunda verifica a consistência concorrente do mesmo serviço. Em reinício, o seed retorna e as chaves são descartadas conforme o ADR.

## Alteração da revisão 2

Aprovada a correção documental de EG-01 a EG-04 conforme o [relatório](spec-review.md). A representação numérica foi fixada para manter o contrato previsível.

## Alteração da revisão 3

Adotado o perfil hexagonal após revisão do código e das responsabilidades. Os CA continuam na revisão 2 do PRD. A tarefa 3.0 valida a direção das dependências e reexecuta o aceite do contrato HTTP, consistência e reinício.
