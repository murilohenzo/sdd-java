# Exemplo — Reserva de estoque

Este diretório é a raiz do componente fictício. Aplique o [guia do kit](../AGENTS.md) e as skills em `../.agents/skills/` quando estiver trabalhando neste componente. As specs ficam em [tasks/prd-reserva-estoque/](tasks/prd-reserva-estoque/) e os ADRs em [docs/architecture/adr/](docs/architecture/adr/).

Use Java 21 e Maven. Confira versões e dependências no [pom.xml](pom.xml). Execute `mvn -B -ntp verify` com diretório de trabalho `example/`. O escopo do exemplo é local e usa apenas dados fictícios.

O componente adota o [perfil hexagonal](../.agents/rules/architectures/hexagonal.md), conforme o [ADR 0002](docs/architecture/adr/0002-ports-and-adapters.md). Confira a direção das dependências com ArchUnit e revise a responsabilidade de cada componente manualmente.
