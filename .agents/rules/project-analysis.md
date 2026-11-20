---
name: project-analysis
description: Delimita descoberta de contexto e impacto no SDD, com leitura seletiva e persistência apenas no artefato adequado.
---

# Análise de contexto e impacto

A análise de contexto faz parte da criação e da revisão material do PRD. Nas etapas seguintes, aprofunde apenas as dúvidas que afetam a entrega.

## Quando aplicar no fluxo

- **Descoberta/PRD** — confirme o fluxo afetado, as fronteiras e as restrições do produto. Produza o blueprint da funcionalidade com a matriz de trade-offs materiais e a avaliação de ADR. Em greenfield, registre a ausência de build e as premissas que sustentam as alternativas.
- **TechSpec** — reutilize o blueprint e aprofunde as lacunas sobre módulos, consumidores, ownership, contratos ou restrições que mudam o desenho. Registre detalhes locais do desenho na TechSpec.
- **Implementação, review ou QA:** quando surgir dependência não prevista ou divergência material. Investigue o trecho afetado e aplique o controle de mudança do SDD; não reinicie a análise inteira.
- **Após a mudança:** atualize os fatos e mapas adotados que ficaram desatualizados. Um commit ou uma tarefa concluída, por si só, não exige nova release de contexto.

Não acione análise ampla para alteração isolada já compreendida, correção de texto ou mera preparação de commit.

## Contexto mínimo e evidências

1. Defina a pergunta a responder e a decisão que depende dela. Comece por referências já disponíveis no perfil, blueprint ou specs. Confira nas fontes os fatos ainda pendentes, os que a mudança afeta e os necessários para resolver divergências.
2. Explore o módulo/fluxo afetado, seus chamadores, consumidores, contratos e testes. Amplie a busca somente quando uma dependência ou evidência justificar. Leia builds e infraestrutura pertinentes, sem inventariar serviços alheios ao escopo.
3. Para uma afirmação que sustente uma decisão, cite o caminho e o símbolo ou configuração que a comprova. Identifique explicitamente inferências, propostas e lacunas. Ao reutilizar uma análise, confira se as fontes que sustentam a conclusão continuam aplicáveis à revisão e ao escopo atuais.
4. No brownfield, preserve o comportamento confirmado fora da mudança autorizada; no greenfield, identifique decisões propostas e premissas. Ausência de evidência não comprova ausência de integração ou garantia arquitetural.

Use código e configuração para confirmar o estado atual e as specs vigentes para identificar a mudança desejada. Vincule regras de negócio a decisões do usuário ou a fontes do projeto. Preserve evidências suficientes sem copiar arquivos extensos ou conteúdo sensível. Uma divergência de implementação pede investigação antes de qualquer mudança no requisito.

## Onde registrar

- **Perfil local:** fatos reutilizáveis do repositório e decisões adotadas, como módulos, versões e comandos. Não armazene backlog ou histórico de execução.
- **PRD, TechSpec e tarefa** — requisitos, solução e evidências da funcionalidade, conforme o [contrato SDD](spec-driven-development.md).
- **Blueprint da funcionalidade** — mapa arquitetural do fluxo afetado, impactos, matriz de alternativas e referências a ADRs.
- **Blueprint macro** — visão compartilhada do sistema, quando solicitada ou já adotada e afetada. Vincule essa visão no blueprint da funcionalidade.
- **ADR** — decisão duradoura e suas consequências, de acordo com a política de arquitetura local.
- **Release de contexto:** diagnóstico transversal que precise de histórico próprio, somente se esse tipo de registro for solicitado ou adotado. Preservar perfil ou TechSpec não implica criar `docs/context/`.

Uma análise pontual pode ser entregue na resposta ou na seção pertinente de um documento existente. Crie somente o artefato necessário e preserve seu histórico; não duplique a mesma descoberta em todos os destinos.

## Skills sob demanda

- [project-context-analyzer](../skills/project-context-analyzer/SKILL.md): diagnóstico mais amplo, onboarding ou análise entre módulos, com ou sem relatório persistido.
- [architecture-blueprint-generator](../skills/architecture-blueprint-generator/SKILL.md) gera o blueprint da funcionalidade ou o mapa macro. Carregue somente as instruções do modo aplicável.
- [avaliar-arquitetura](../skills/avaliar-arquitetura/SKILL.md) orienta a matriz de trade-offs e o registro de decisões duradouras.
- [archunit-rules](../skills/archunit-rules/SKILL.md): criação ou revisão de validação arquitetural automatizada; não é necessária apenas para ler dependências.

## Quando encerrar

Encerre quando a pergunta estiver respondida, o impacto relevante estiver delimitado e a próxima decisão/etapa tiver evidências suficientes. Entregue conclusão, fontes, impacto e pendências materiais; em persistência, confira links e consistência dos artefatos alterados. Se faltar evidência para uma decisão, indique a lacuna e o que a resolve, sem prolongar a busca por assuntos independentes.

