# Exemplos de aplicação

## Release brownfield

```markdown
## Problema P1 — dependência direta entre bancos

**Estado:** observado. `servico-b` consulta tabelas pertencentes a `servico-a`.
**Impacto:** alto acoplamento de schema e deploy; baixa autonomia de evolução.
**Melhoria recomendada:** publicar um evento versionado e manter uma projeção mínima em `servico-b`.
**Sequência:** definir contrato, consumir com idempotência, migrar leitura, remover acesso externo.
**Conclusão:** nenhum acesso SQL ao schema de `servico-a` e testes de duplicidade aprovados.
```

## Relação de comunicação

```markdown
| `servico-contas` | `servico-catalogo` | publica ciclo de vida de conta | RabbitMQ + `AccountEvent.v1` | Não | referência mínima | `servico-contas` | `AccountEventListener.java` |
```

## Projeto greenfield

```markdown
## Arquitetura proposta

`catalog-api` será dono do catálogo e exporá HTTP. `search-indexer` receberá eventos assíncronos para construir um índice próprio.

**Premissas a validar:** broker escolhido, garantia de entrega, estratégia de reprocessamento e ownership do índice.
**Não confirmado:** portas, nomes de filas e schema final.
```

