# ADR 0001 — Limite de consistência por processo

- Estado — aceito para o cenário demonstrativo.
- Data — 2026-11-20.
- Origem — decisão de desenho do piloto delegada ao agente pelo pedido de implementação greenfield.
- [PRD](../../../tasks/prd-reserva-estoque/prd.md) · [Blueprint e matriz](../../../tasks/prd-reserva-estoque/blueprint.md) · [TechSpec](../../../tasks/prd-reserva-estoque/techspec.md).

## Decisão

Manter saldo e chaves confirmadas na mesma instância do serviço. Serializar a verificação da chave, a leitura do saldo e a gravação de uma confirmação em uma seção crítica. Usar um processo com configuração HTTP local.

## Consequências e verificação

O exemplo é inicializável sem banco ou broker. Reiniciar o processo restaura o saldo inicial e descarta as confirmações. O consumidor conta com replay somente durante a vida da instância. TU-04 e TU-05 verificam consistência. MAN-01 verifica escuta local e reinício (CA-06). Persistência ou múltiplas instâncias exigem reconsiderar a decisão para garantir atomicidade e unicidade compartilhadas.
