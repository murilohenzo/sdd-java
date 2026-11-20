---
name: criar-techspec
description: Cria ou atualiza TechSpec a partir de um PRD, com solução técnica, contratos e verificações rastreáveis. Use para desenhar uma funcionalidade especificada; não para definir requisitos do produto ou implementar.
---

# Criar TechSpec

## Entrada e leitura

Resolva o slug conforme [o contrato SDD](../../rules/spec-driven-development.md). Use o PRD pronto para desenho, o blueprint da funcionalidade e os ADRs pertinentes. Leia o perfil quando existir, a TechSpec existente e [o template](references/TEMPLATE.md). Aplique o tratamento de pré-requisitos da regra comum quando houver lacuna, inclusive a compatibilidade com PRDs anteriores ao blueprint.

## Procedimento

1. Identifique RF/RNF, CA, restrições e exclusões. Reuse os fatos do blueprint e confira nas fontes do escopo as versões, os módulos, contratos, configurações e testes necessários às decisões. Em greenfield, rotule escolhas como propostas e registre as evidências ainda ausentes.
2. Carregue as regras e skills pertinentes ao desenho. Investigue os chamadores, consumidores e pontos de extensão que o blueprint deixou em aberto. Consulte documentação primária compatível com as versões quando necessário.
3. Separe estado atual da solução proposta. Nomeie arquivos/componentes a criar ou modificar, responsabilidades, fluxo, contratos, compatibilidade, falhas e riscos relevantes. Referencie contratos versionados; não mantenha cópias concorrentes de schemas.
4. Use a matriz para justificar a abordagem e resolver pendências de desenho. Quando surgir uma alternativa ou evidência material, reavalie com [avaliar-arquitetura](../avaliar-arquitetura/SKILL.md) e atualize a análise afetada. Vincule os ADRs aplicáveis e registre suas consequências no desenho. Se a escolha alterar escopo ou aceite, reconcilie o PRD antes de fechar o desenho.
5. Mapeie cada CA a casos `TU-*`, `TI-*`, `TC-*`, `E2E-*` ou `MAN-*`, com cenário, resultado esperado e meio de execução. Escolha o menor nível suficiente e registre infraestrutura necessária. Não crie E2E visual para serviço sem UI.
6. Salve `techspec.md` com revisão do PRD de origem, links para a análise aplicada, estado, fontes e pendências. Confira cobertura dos CA, riscos de migração quando aplicáveis e conformidade com as decisões e regras relevantes.

## Saída e conclusão

Informe arquivo, estado e decisões pendentes. `pronto` requer o critério da etapa no contrato SDD. Não altere código de aplicação durante um pedido restrito ao desenho.
