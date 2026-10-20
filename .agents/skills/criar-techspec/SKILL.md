---
name: criar-techspec
description: Cria ou atualiza TechSpec a partir de um PRD, com solução técnica, contratos e verificações rastreáveis. Use para desenhar uma funcionalidade especificada; não para definir requisitos do produto ou implementar.
---

# Criar TechSpec

## Entrada e leitura

Resolva o slug conforme [o contrato SDD](../../rules/spec-driven-development.md). Entrada: `tasks/prd-<slug>/prd.md` pronto para desenho. Leia-o integralmente, o perfil quando existir, a TechSpec existente quando houver e [o template](references/TEMPLATE.md). Se o PRD faltar ou tiver bloqueador material, siga o tratamento de pré-requisitos da regra comum.

## Procedimento

1. Identifique RF/RNF, CA, restrições e exclusões. Confirme versões, módulos, contratos, configuração e testes nas fontes do escopo. Em greenfield, rotule escolhas como propostas, sem fingir que existe build.
2. Carregue regras de arquitetura/código/testes e skills técnicas somente quando aplicáveis. Investigue chamadores, consumidores e pontos de extensão com as ferramentas disponíveis. Consulte documentação primária compatível com as versões quando necessário.
3. Separe estado atual da solução proposta. Nomeie arquivos/componentes a criar ou modificar, responsabilidades, fluxo, contratos, compatibilidade, falhas e riscos relevantes. Referencie contratos versionados; não mantenha cópias concorrentes de schemas.
4. Resolva ambiguidades materiais; registre alternativas e justificativa das decisões relevantes. Escolhas locais reversíveis não exigem aprovação mecânica.
5. Mapeie cada CA a casos `TU-*`, `TI-*`, `TC-*`, `E2E-*` ou `MAN-*`, com cenário, resultado esperado e meio de execução. Escolha o menor nível suficiente e registre infraestrutura necessária. Não crie E2E visual para serviço sem UI.
6. Salve `techspec.md` com revisão do PRD de origem, estado, fontes e pendências. Confira cobertura dos CA, riscos de migração quando aplicáveis e conformidade com as regras efetivamente relevantes.

## Saída e conclusão

Informe arquivo, estado e decisões pendentes. `pronto` requer o critério da etapa no contrato SDD. Não altere código de aplicação durante um pedido restrito ao desenho.
