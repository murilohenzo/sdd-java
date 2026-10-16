---
name: project-analysis
description: Delimita descoberta de contexto e impacto no SDD, com leitura seletiva e persistência apenas no artefato adequado.
---

# Análise de contexto e impacto

A análise apoia as etapas do SDD sob demanda. Não é uma etapa adicional obrigatória nem exige uma auditoria completa antes de cada tarefa.

## Quando aplicar no fluxo

- **Descoberta/PRD:** quando limites do produto existente ou integrações desconhecidas afetarem o escopo. Falta de build em greenfield não impede definir requisitos.
- **TechSpec:** quando for necessário esclarecer módulos, consumidores, ownership, contratos ou restrições que mudam o desenho. Uma descoberta local simples pode ficar na própria TechSpec.
- **Implementação, review ou QA:** quando surgir dependência não prevista ou divergência material. Investigue o trecho afetado e aplique o controle de mudança do SDD; não reinicie a análise inteira.
- **Após a mudança:** atualize os fatos e mapas adotados que ficaram desatualizados. Um commit ou uma tarefa concluída, por si só, não exige nova release de contexto.

Não acione análise ampla para alteração isolada já compreendida, correção de texto ou mera preparação de commit.

## Contexto mínimo e evidências

1. Defina a pergunta a responder e a decisão/etapa que depende dela. Reuse o perfil e o contexto já lido; confira nas fontes apenas fatos relevantes ainda não verificados ou afetados por mudanças.
2. Explore o módulo/fluxo afetado, seus chamadores, consumidores, contratos e testes. Amplie a busca somente quando uma dependência ou evidência justificar. Leia builds e infraestrutura pertinentes, sem inventariar serviços alheios ao escopo.
3. Separe estado observado, inferências, propostas e pendências. Cite caminho e símbolo/configuração que sustentam afirmações relevantes. Data ou resumo antigo não prova que o contexto continua válido.
4. No brownfield, preserve o comportamento confirmado fora da mudança autorizada; no greenfield, identifique decisões propostas e premissas. Ausência de evidência não comprova ausência de integração ou garantia arquitetural.

Código/configuração comprovam o estado atual; specs vigentes definem o desejado. Não corrija um requisito para esconder um defeito. Preserve evidências suficientes sem copiar arquivos extensos ou conteúdo sensível.

## Onde registrar

- **Perfil local:** fatos reutilizáveis do repositório e decisões adotadas, como módulos, versões e comandos. Não armazene backlog ou histórico de execução.
- **PRD/TechSpec/tarefa:** requisitos, solução e evidências específicos da funcionalidade, cada qual no artefato definido pela regra SDD. Referencie contexto compartilhado em vez de copiá-lo.
- **Blueprint:** visão arquitetural compartilhada, quando solicitada ou já adotada e afetada. Diferencie arquitetura observada de evolução proposta.
- **Release de contexto:** diagnóstico transversal que precise de histórico próprio, somente se esse tipo de registro for solicitado ou adotado. Preservar perfil ou TechSpec não implica criar `docs/context/`.

Uma análise pontual pode ser entregue na resposta ou na seção pertinente de um documento existente. Crie somente o artefato necessário e preserve seu histórico; não duplique a mesma descoberta em todos os destinos.

## Skills sob demanda

- [project-context-analyzer](../skills/project-context-analyzer/SKILL.md): diagnóstico mais amplo, onboarding ou análise entre módulos, com ou sem relatório persistido.
- [architecture-blueprint-generator](../skills/architecture-blueprint-generator/SKILL.md): criação/atualização efetiva do blueprint. A simples existência desse documento não exige carregá-la.
- [archunit-rules](../skills/archunit-rules/SKILL.md): criação ou revisão de validação arquitetural automatizada; não é necessária apenas para ler dependências.

## Quando encerrar

Encerre quando a pergunta estiver respondida, o impacto relevante estiver delimitado e a próxima decisão/etapa tiver evidências suficientes. Entregue conclusão, fontes, impacto e pendências materiais; em persistência, confira links e consistência dos artefatos alterados. Se faltar evidência para uma decisão, indique a lacuna e o que a resolve, sem prolongar a busca por assuntos independentes.

