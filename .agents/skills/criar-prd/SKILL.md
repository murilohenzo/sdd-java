---
name: criar-prd
description: Cria ou atualiza PRD com requisitos, critérios de aceitação e blueprint da funcionalidade. Usa a análise de impacto e trade-offs para definir o escopo antes do desenho técnico.
---

# Criar PRD

## Entrada e leitura

Use o pedido e o contexto do produto disponível. Leia [o contrato SDD](../../rules/spec-driven-development.md), [a regra de análise](../../rules/project-analysis.md), o perfil local quando existir e [o template](references/TEMPLATE.md). Identifique o slug e preserve o histórico de revisões e os IDs do PRD existente. Em correções editoriais, limite a atualização aos trechos e referências afetados.

## Procedimento

1. Delimite problema, público, resultado desejado e escopo preliminar. Para cada RF/RNF, escreva CA com condição inicial, ação ou evento e resultado esperado que QA possa distinguir de uma falha. Cubra caminhos de sucesso, erro e limite quando mudarem o comportamento exigido.
2. Durante a descoberta, use o modo funcionalidade de [architecture-blueprint-generator](../architecture-blueprint-generator/SKILL.md). Confirme pontos de entrada, fronteiras, consumidores, contratos e restrições relevantes. No greenfield, registre a ausência de implementação e explicite as premissas.
3. Use a matriz e a avaliação de ADR do blueprint para identificar as escolhas que mudam o produto. Indique decisões que podem seguir para a TechSpec e a evidência que falta.
4. Revise o escopo, RF/RNF e CA à luz dos impactos. Pergunte apenas por lacunas materiais de produto ou restrições que impeçam fixar o aceite ou o escopo. Preserve decisões dadas, rotule propostas e registre premissas reversíveis. Use metas com fonte confirmada.
5. Salve `tasks/prd-<slug>/prd.md` e `blueprint.md` com links entre si e a revisão do PRD analisada. Confira aceite de cada requisito, coerência com exclusões e prontidão para desenho. Em revisões materiais, identifique os artefatos dependentes a reconciliar.

## Saída e conclusão

Entregue o PRD e o blueprint, com estado, escolhas que afetam o produto, ADRs aplicáveis e pendências para o desenho. Só marque o PRD como `pronto` após a checagem. Avance à TechSpec ou à implementação somente quando o pedido abranger essas etapas.
