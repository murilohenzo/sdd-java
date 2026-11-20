---
name: validar-spec
description: Verifica ambiguidades, rastreabilidade e coerência entre PRD, blueprint, ADRs, TechSpec e tarefas, com modo independente Elephant-Goldfish. Use ao liberar uma etapa do SDD ou revisar especificações alteradas.
---

# Validar especificação

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md) e [o template](references/TEMPLATE.md). Resolva a funcionalidade e a etapa que se quer liberar. Leia o PRD, o blueprint e os artefatos pertinentes em `tasks/prd-<slug>/`, além dos ADRs vinculados que afetem a etapa.

Para liberar o desenho, confira o PRD e o blueprint quando exigido. Para liberar o planejamento de tarefas, inclua a TechSpec. Para liberar a implementação, inclua também o plano e as tarefas.

Use [o procedimento Elephant-Goldfish](references/elephant-goldfish.md) quando o usuário solicitar revisão independente ou uma convenção local autorizá-la. É especialmente útil quando uma interpretação errada afetaria contratos, dados, concorrência ou múltiplos módulos. Nesse modo, o revisor assume a checagem de compreensão e prontidão no escopo selecionado. O agente principal consolida os achados e confere a rastreabilidade.

## Procedimento

1. Identifique revisões, fontes e decisões relevantes para a etapa. Se as revisões divergirem, verifique quais requisitos, restrições ou decisões mudaram e avalie os vínculos afetados.
2. Leia cada CA como um caso a verificar. Confira condições, ação ou evento, resultado observável e origem. Procure interpretações que produziriam aceites diferentes, metas sem fonte, suposições tratadas como fatos e conflitos com restrições ou exclusões.
3. Confira o blueprint exigido para a revisão do PRD. Verifique fontes do estado observado, fronteiras afetadas, critérios comuns da matriz ou justificativa para dispensá-la e avaliação da necessidade de ADR. Uma decisão proposta pode seguir para desenho se escopo, restrições e aceite estiverem claros.
4. Quando houver TechSpec, confira solução, ADRs vigentes e verificações de cada CA. Para o perfil arquitetural adotado, verifique se o desenho explicita responsabilidades, direção das dependências e como serão verificados os limites relevantes. Procure consequências arquiteturais sem tratamento e divergências materiais.
5. Quando houver tarefas, percorra `RF/RNF → CA → verificação → tarefa` nos dois sentidos. Confira IDs, cobertura, propósito das entregas, dependências, viabilidade das verificações e critérios de conclusão.
6. Cite o ID ou trecho, a consequência para a etapa e a correção sugerida. Explique qual obrigação cada bloqueador impede. Registre melhorias e decisões previstas para etapas posteriores conforme o impacto. Faça a análise documental. Altere os artefatos analisados somente quando o pedido incluir correções.
7. Salve `spec-review.md` com `PRONTO` ou `BLOQUEADO` **para a etapa avaliada**. Após correções autorizadas, repita as checagens afetadas.

## Saída e conclusão

Informe etapa, veredito, lacunas e próximos passos. Ausência de achados em análise documental não prova execução nem aprovação humana.
