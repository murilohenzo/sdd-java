---
name: validar-spec
description: Analisa consistência, ambiguidades e rastreabilidade de PRD, TechSpec e tarefas antes da implementação ou após mudança de requisitos. Use para validar especificações; não para revisar código ou executar QA.
---

# Validar especificação

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md) e [o template](references/TEMPLATE.md). Resolva a funcionalidade e a etapa que se quer liberar. Leia o PRD e os demais artefatos já existentes em `tasks/prd-<slug>/`.

Não exija tarefas para validar somente um PRD. Para validar prontidão para implementação, PRD, TechSpec e tarefas são necessários.

## Procedimento

1. Identifique revisões, estado, fontes, decisões do usuário e pendências. Confira se os artefatos descrevem a mesma funcionalidade e versão de escopo.
2. Procure requisitos ambíguos, contraditórios, não verificáveis ou sem fonte; metas inventadas; suposições tratadas como fatos; conflitos com restrições e exclusões.
3. Quando houver TechSpec, confira cada CA contra solução e verificações com resultados esperados. Diferencie tecnologia observada e proposta; use apenas regras técnicas aplicáveis.
4. Quando houver tarefas, percorra `RF/RNF → CA → verificação → tarefa` nos dois sentidos. Detecte IDs inexistentes/duplicados, obrigação sem cobertura, tarefa sem propósito e dependências ausentes ou cíclicas.
5. Confira critérios de conclusão, comandos/ambiente, riscos de compatibilidade/migração pertinentes e viabilidade das verificações. Não execute código de produto nesta análise.
6. Classifique cada achado como bloqueador da etapa ou melhoria, indicando evidência, consequência e correção sugerida. Não reescreva documentos como efeito colateral de um pedido restrito à análise.
7. Salve `spec-review.md` com `PRONTO` ou `BLOQUEADO` **para a etapa avaliada**. Se o pedido incluir correções, atualize os artefatos autorizados, preserve decisões e repita as checagens afetadas.

## Saída e conclusão

Informe etapa, veredito, lacunas e próximos passos. Ausência de achados em análise documental não prova execução nem aprovação humana.
