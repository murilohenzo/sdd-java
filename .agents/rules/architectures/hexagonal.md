---
name: architecture-hexagonal
description: Perfil opcional para projetos que adotam arquitetura Hexagonal ou Ports and Adapters.
---

# Perfil arquitetural — Hexagonal/Ports and Adapters

Aplicar somente quando o projeto declarar esta arquitetura. O nome exato dos pacotes pode variar; responsabilidades e direção das dependências são mais importantes que a árvore física.

## Invariantes

- O domínio não depende de Spring, HTTP, persistência, mensageria ou implantação.
- A aplicação orquestra casos de uso e depende do domínio.
- Ports definem os contratos necessários para entrada e saída.
- Adapters inbound traduzem protocolos externos para casos de uso.
- Adapters outbound implementam integrações exigidas pelo core.
- Infraestrutura compõe beans e tecnologias sem inverter a direção das dependências internas.

## Estrutura de referência

```text
<root>/
├── core/
│   ├── domain/
│   └── application/
├── adapters/
│   ├── inbound/
│   └── outbound/
└── infrastructure/
```

Esta árvore é uma referência, não um requisito literal. Projetos organizados primeiro por feature podem repetir essas responsabilidades dentro de cada contexto.

## Decisões que devem ser explícitas

- localização das ports de entrada e saída;
- camada que implementa cada contrato;
- fronteira transacional;
- permissão ou proibição de dependências entre features;
- localização de DTOs, entidades ORM e modelos de mensageria;
- forma de composição Spring.

Não mantenha convenções paralelas, como `in` e `inbound`, sem uma decisão registrada. Quando houver legado, preserve compatibilidade e evolua incrementalmente.
