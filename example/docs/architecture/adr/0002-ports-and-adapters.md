# ADR 0002 — Arquitetura hexagonal da reserva

- Estado — aceito para o exemplo.
- Data — 2026-11-20.
- Origem — revisão do exemplo solicitou arquitetura hexagonal e aplicação do princípio da responsabilidade única.
- [Perfil hexagonal do kit](../../../../.agents/rules/architectures/hexagonal.md) · [Blueprint](../../../tasks/prd-reserva-estoque/blueprint.md) · [TechSpec](../../../tasks/prd-reserva-estoque/techspec.md).

## Contexto e decisão

Na revisão 2, `ReservationService` era um bean Spring que aplicava as regras de reserva, armazenava o estado, definia a carga inicial e sincronizava as operações. O controller dependia dessa implementação e usava seus tipos no contrato HTTP. A organização por serviço e controller exigia poucas classes. Adotar Ports and Adapters explicita o limite entre a política de reserva e os mecanismos usados para expor e armazenar o estado, ao custo de dois contratos e uma configuração de composição.

- `core.domain` detém as invariantes de saldo e idempotência no mesmo agregado, que depende apenas do JDK.
- `core.application` implementa a porta de entrada `ReserveStock` e executa a reserva por meio da porta de saída `ReservationStore`.
- `adapters.inbound.http` traduz HTTP para a porta de entrada. As regras de formato, a tradução de erros e os DTOs HTTP pertencem a essa borda.
- `adapters.outbound.memory` implementa o acesso exclusivo ao estado em memória. A decisão de debitar, confirmar ou rejeitar continua no domínio.
- `infrastructure` fornece a carga inicial e compõe beans Spring. O endereço local é configurado na aplicação.

## Consequências e verificação

A disponibilidade e as chaves confirmadas compartilham a seção crítica definida no [ADR 0001](0001-consistencia-em-memoria.md). A porta de saída executa a operação de forma síncrona enquanto detém esse limite. Uma rejeição de domínio sai antes de qualquer mutação. Um armazenamento durável ou concorrência entre processos exigiria rever o contrato de consistência.

O check `ARQ-01` verifica dependências e localização das classes compiladas. A revisão de código verifica a responsabilidade de cada componente pelas causas de mudança, como alteração no contrato HTTP, na política de reserva ou no armazenamento.
