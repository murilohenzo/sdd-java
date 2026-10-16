---
name: criar-tasks
description: Decompõe PRD e TechSpec em entregas incrementais com dependências e rastreabilidade. Use para criar ou atualizar o plano de tarefas de uma funcionalidade especificada; não para implementá-la.
---

# Criar tarefas

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md), `prd.md` e `techspec.md` em `tasks/prd-<slug>/`, ambos vigentes e prontos para a etapa. Se já houver tarefas, leia seu índice e preserve IDs, histórico e evidências válidas.

Use [o template do índice](references/TEMPLATE_TASKS.md) e [o template de tarefa](references/TEMPLATE_TASK.md). Consulte somente regras e skills necessárias para delimitar as entregas.

## Procedimento

1. Inventarie RF/RNF, CA, decisões e casos de verificação. Identifique lacunas antes de converter um documento incompleto em tarefas aparentemente executáveis.
2. Organize entregas pequenas e verificáveis, preferencialmente por comportamento completo. Separe infraestrutura habilitadora quando necessária e indique a entrega que ela viabiliza. Não imponha um número fixo de tarefas.
3. Declare dependências por ID, valide sua existência e ausência de ciclos. A ordem deve permitir testar cada incremento. Independência pode ser registrada, mas não pressupõe execução paralela ou subagentes.
4. Grave `tasks.md` e `task_<n>.md`, com números estáveis. Inclua escopo, subtarefas, referências técnicas, testes, comandos e critérios de conclusão. Não replique contratos ou toda a TechSpec.
5. Complete o mapa `RF/RNF → CA → verificação → tarefa`. Nenhuma obrigação pode ficar sem destino; tarefas sem requisito direto devem justificar sua função habilitadora.
6. Confira estado inicial, revisões de origem e retomada. Uma nova versão de specs exige reavaliar apenas tarefas impactadas, preservando o restante.

## Saída e conclusão

Entregue arquivos concretos para revisão, estado e próxima tarefa elegível. Uma solicitação de planejamento autoriza gerar os documentos; não exige aprovar uma lista antes da escrita. Implemente somente se o usuário já tiver solicitado também a execução.
