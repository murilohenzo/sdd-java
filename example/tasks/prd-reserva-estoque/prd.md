# PRD — Reserva de estoque

- Estado — pronto.
- Revisão — 2.
- Atualizado em — 2026-11-20.
- Origem — cenário demonstrativo fictício definido para o piloto SDD.
- [Blueprint da funcionalidade](blueprint.md).

## Problema e escopo

Um consumidor de pedidos precisa reservar unidades de um item e poder repetir a confirmação quando perder a resposta. O piloto disponibiliza a operação de reserva, com um estoque inicial conhecido que permite verificar os efeitos. O exemplo é local, com um processo e dados descartáveis. Pagamento, expiração, cancelamento, autenticação e persistência entre reinícios estão fora do escopo.

## Requisitos

- **RF-01** — Criar uma reserva identificável para um SKU existente com saldo suficiente. Uma confirmação reduz a disponibilidade na quantidade solicitada.
- **RF-02** — Reconhecer o reenvio de uma reserva confirmada pela chave de idempotência. Uma chave confirmada pertence à combinação original de SKU e quantidade.
- **RF-03** — Rejeitar solicitações inválidas, itens inexistentes ou pedidos sem saldo disponível.
- **RNF-01** — Manter a consistência do estoque e das chaves quando solicitações se sobrepuserem dentro do mesmo processo.
- **RNF-02** — Executar localmente e restaurar o cenário inicial a cada reinício.

## Critérios de aceitação

- **CA-01 (RF-01)** — Dado um item com dez unidades e uma solicitação válida de seis com chave nova, confirmar a reserva com identificador e deixar quatro unidades disponíveis.
- **CA-02 (RF-02)** — Dada uma reserva confirmada, enviar novamente a mesma chave, SKU e quantidade devolve a reserva original sem novo débito.
- **CA-03 (RF-02)** — Dada uma chave confirmada, reutilizá-la com SKU ou quantidade diferente resulta em conflito e preserva a reserva original e o saldo.
- **CA-04 (RF-03)** — Uma chave ausente ou inválida, um SKU inválido ou uma quantidade ausente ou inválida produz erro de entrada. Um SKU válido desconhecido produz erro de item inexistente. Um pedido acima do saldo produz erro de saldo insuficiente. Cada rejeição preserva a disponibilidade.
- **CA-05 (RNF-01)** — Com dez unidades, duas solicitações concorrentes de seis com chaves diferentes resultam em uma confirmação, uma rejeição por falta de saldo e quatro unidades remanescentes. Se as solicitações forem equivalentes e usarem a mesma chave, retornam a mesma reserva e o saldo é debitado uma vez.
- **CA-06 (RNF-02)** — A aplicação aceita conexões pela interface de loopback. Após confirmar dez unidades e reiniciar, enviar a mesma chave e dados confirma uma nova reserva de dez unidades com outro identificador.

## Premissas

O ambiente usa um SKU fictício com dez unidades ao iniciar. Após reinício o estoque retorna ao cenário inicial. A chave identifica uma operação dentro da instância em execução. Os detalhes do contrato HTTP estão na [TechSpec](techspec.md).

## Alteração da revisão 2

RNF-02 e CA-06 tornam verificáveis as obrigações de execução local e reinício já descritas na revisão 1. A [revisão independente](spec-review.md) motivou a rastreabilidade explícita.
