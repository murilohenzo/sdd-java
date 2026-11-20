# Blueprint — Reserva de estoque

- PRD analisado — [revisão 2](prd.md).
- Atualizado em — 2026-11-20.
- Base observada na descoberta — kit SDD e repositório sem código Java para este componente. O mapa abaixo descreve a arquitetura proposta para o exemplo.

## Fluxo e fronteiras

O consumidor de pedidos chama a API de reserva pela interface de loopback. O controller valida e traduz o contrato HTTP. O serviço possui o saldo e o registro das chaves confirmadas. A confirmação e o débito precisam ocorrer no mesmo limite de consistência (CA-01 a CA-05). O reinício recria o estado da instância (CA-06).

```mermaid
flowchart LR
    Cliente["Consumidor de pedidos"] -->|"POST de reserva · proposto"| MVC["Controller MVC"]
    MVC -->|"SKU, quantidade e chave"| Servico["Serviço de reserva"]
    Servico -->|"Acesso sincronizado"| Memoria["Saldo e chaves da instância"]
```

## Matriz de impacto e trade-offs

**Decisão** — armazenar o saldo e as chaves durante o piloto. A configuração deve cumprir RF-01, RF-02 e RNF-01 no processo de exemplo.

| Alternativa | Ganhos | Custos e perdas | Impacto e evidência |
| --- | --- | --- | --- |
| Estado em memória com sincronização única | Reproduzível com um processo e sem banco. Uma seção crítica cobre saldo e chave. | Estado reinicia com o processo. Concorrência serializada no componente. | Satisfaz o escopo local do PRD. A consistência será verificada com pedidos concorrentes. |
| Banco relacional com transação e unicidade da chave | Permite preservar reservas entre reinícios. Pode coordenar múltiplas instâncias. | Requer schema, migração, configuração e testes de persistência. | O PRD admite reinício do cenário e uma única instância. Adotar o banco ampliaria o ambiente de execução. |

Escolha do piloto — estado em memória. O custo aceito é perder os dados no reinício. Se persistência ou múltiplas instâncias entrarem no escopo, reavaliar a fronteira do serviço e a estratégia de consistência.

## Decisão arquitetural

[ADR 0001](../../docs/architecture/adr/0001-consistencia-em-memoria.md) registra o limite de consistência e a vida útil da idempotência. A [TechSpec](techspec.md) converte a decisão em contrato e verificações.
