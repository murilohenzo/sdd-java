---
name: architecture-blueprint-generator
description: Gera o blueprint de uma funcionalidade durante o PRD ou documenta a arquitetura macro de um projeto. Use para descrever fronteiras, responsabilidades, dependências e fluxos com evidências do repositório.
---

# Gerador de blueprint arquitetural

## Escolha o escopo

- **Funcionalidade** — escreva `tasks/prd-<slug>/blueprint.md` durante a criação ou revisão material do PRD. Siga o procedimento abaixo e reutilize o mapa macro quando existir.
- **Macro** — documente o projeto ou módulo quando solicitado ou adotado localmente. Use `docs/architecture/Project_Architecture_Blueprint.md` ou o caminho indicado. Consulte [o fluxo macro](references/macro-workflow.md) somente nesse caso.

## Blueprint da funcionalidade

1. Use o escopo preliminar, os requisitos e a revisão do PRD. Aplique a leitura seletiva da [regra de análise](../../rules/project-analysis.md). Confira o perfil e o código, build, configuração, contratos ou testes que sustentem o fluxo afetado.
2. Adapte [o template da funcionalidade](references/TEMPLATE_FEATURE_BLUEPRINT.md). Explique os pontos de entrada, responsabilidades, dependências, consumidores e dados afetados. Cite evidências para o estado atual e diferencie inferências, propostas e pendências. Quando não houver aplicação ou build, registre essa lacuna.
3. Use [avaliar-arquitetura](../avaliar-arquitetura/SKILL.md) para incorporar a matriz e avaliar ADRs. Registre efeitos sobre o PRD e informações a levar à TechSpec. Quando um diagrama ajudar, identifique explicitamente fluxos propostos.
4. Confira se o blueprint cobre as fronteiras afetadas, vincula a revisão do PRD analisada e permite localizar as fontes. Atualize somente o mapa necessário à funcionalidade.

