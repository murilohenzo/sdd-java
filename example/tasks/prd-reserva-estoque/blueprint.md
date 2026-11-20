# Blueprint — Reserva de estoque

- PRD analisado — [revisão 2](prd.md).
- Atualizado em — 2026-11-20.
- Base na descoberta — repositório sem código Java para este componente. A revisão 2 implementou controller e serviço no mesmo pacote. A revisão 3 adota Ports and Adapters segundo o ADR 0002.

## Fluxo e fronteiras

O consumidor chama a API em loopback. O adaptador HTTP traduz o contrato e chama a porta de entrada. O caso de uso pede acesso exclusivo ao adaptador de memória pela porta de saída. Durante a operação, o agregado decide débito e replay (CA-01 a CA-05). A configuração Spring cria o estado inicial a cada reinício (CA-06).

```mermaid
flowchart LR
    Cliente["Consumidor de pedidos"] -->|"POST de reserva"| MVC["Adaptador HTTP"]
    MVC --> Entrada["Porta ReserveStock"]
    Entrada --> Caso["Caso de uso"]
    Caso --> Saida["Porta ReservationStore"]
    Saida --> Memoria["Adaptador em memória"]
    Memoria -->|"Operação sob acesso exclusivo"| Dominio["Agregado de saldo e confirmações"]
```

## Matriz de impacto e trade-offs

**Decisão** — armazenar o saldo e as chaves durante o piloto. A configuração deve cumprir RF-01, RF-02 e RNF-01 no processo de exemplo.

| Alternativa | Ganhos | Custos e perdas | Impacto e evidência |
| --- | --- | --- | --- |
| Estado em memória com sincronização única | Reproduzível com um processo e sem banco. Uma seção crítica cobre saldo e chave. | Estado reinicia com o processo. Concorrência serializada no componente. | Satisfaz o escopo local do PRD. A consistência será verificada com pedidos concorrentes. |
| Banco relacional com transação e unicidade da chave | Permite preservar reservas entre reinícios. Pode coordenar múltiplas instâncias. | Requer schema, migração, configuração e testes de persistência. | O PRD admite reinício do cenário e uma única instância. Adotar o banco ampliaria o ambiente de execução. |

Escolha do piloto — estado em memória. O custo aceito é perder os dados no reinício. Se persistência ou múltiplas instâncias entrarem no escopo, reavaliar a fronteira do serviço e a estratégia de consistência.

## Decisão arquitetural

[ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md) registra o limite de consistência e a vida útil da idempotência. O [ADR 0002](../../docs/architecture/adr/0002-ports-and-adapters.md) define a direção das dependências e as responsabilidades. A [TechSpec](techspec.md) vincula as decisões a verificações.
