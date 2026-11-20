# PRD — [funcionalidade]

- Estado: [rascunho | pronto | desatualizado]
- Revisão: [número]
- Atualizado em: [AAAA-MM-DD]
- Origem: [pedido e fontes do produto; distinguir decisões de propostas]
- Blueprint da funcionalidade — [blueprint.md](blueprint.md).

## Problema e resultado esperado

[Para quem, qual problema e qual resultado observável. Métrica/meta apenas quando conhecida; registrar o que ainda precisa de decisão.]

## Escopo e exclusões

- Incluído: [capacidades e limites].
- Fora do escopo: [exclusões explícitas].

## Histórias e requisitos

- US-01: Como [ator], quero [capacidade] para [resultado].
- RF-01: [comportamento funcional e regra de negócio; origem].
- RNF-01: [restrição mensurável, condição de medição e origem; quando aplicável].

[Inclua cenários relevantes de falha, limites, permissões e dados. Não invente tecnologia para preencher o documento.]

## Critérios de aceitação

- CA-01 — RF-01: Dado [ator, estado inicial e dados relevantes], quando [ação ou evento], então [resposta ou efeito esperado, observável].
- CA-02 — [RF/RNF]: Dado [condição de falha, limite ou restrição], quando [ação ou evento], então [resultado esperado e efeito que deve ser evitado, quando relevante].

[Separe resultados que possam falhar independentemente, preservando cenários em que a combinação de efeitos é parte do aceite. Substitua termos como “rápido” ou “adequado” por condições verificáveis com fonte. Registre como pendência qualquer limiar que ainda dependa de decisão. Preserve IDs existentes.]

## Experiência e restrições

[Jornada, integrações obrigatórias, segurança e privacidade pertinentes. UI, acessibilidade, desempenho e conformidade somente quando aplicáveis; não escolha arquitetura de implementação aqui.]

## Premissas, dependências e pendências

- [Premissa reversível e impacto se incorreta.]
- [Pendência material, decisão necessária e etapa afetada; ou nenhuma.]

## Impactos da análise arquitetural

[Registre efeitos confirmados sobre escopo, restrições ou aceite, referenciando RF/RNF/CA. As alternativas, riscos técnicos e ADRs ficam vinculados no blueprint. Remova esta seção se a análise não mudar o PRD.]

## Alterações desta revisão

[O que mudou, motivo/origem e artefatos impactados. Na primeira revisão, indique criação.]
