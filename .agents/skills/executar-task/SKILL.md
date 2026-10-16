---
name: executar-task
description: Implementa ou retoma tarefas de uma funcionalidade com PRD, TechSpec e plano existentes, validando o resultado e atualizando o progresso. Não use para pedidos restritos a planejamento, revisão ou QA.
---

# Executar tarefa

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md) e [execução e evidências](../../rules/execution.md). Resolva a funcionalidade e confira `prd.md`, `techspec.md`, `tasks.md` e a tarefa selecionada em `tasks/prd-<slug>/`.

## Procedimento

1. Use a tarefa explicitamente indicada. Em retomada, priorize a tarefa em andamento identificada pelo contexto ou pelo registro de próxima ação; reavalie seu bloqueio, se houver. Sem alvo definido, selecione a próxima pendente cujas dependências estejam concluídas. Se houver mais de uma retomada plausível, esclareça o alvo; se nenhuma tarefa for elegível, informe a cadeia de bloqueio. Não pule uma dependência por ela vir depois no arquivo.
2. Confira o estado real do código, as revisões das specs e o contexto anterior da tarefa. Verifique clareza dos CA, cobertura dos testes e ausência de contradições materiais antes de implementar.
3. Leia apenas as seções relevantes das specs, regras aplicáveis e skills técnicas necessárias. Confirme versões, pontos de extensão e comandos de validação no projeto. Não inicie aplicação ou serviços para uma tarefa que não precisa deles.
4. Registre `em_andamento`. Implemente as subtarefas no escopo solicitado; para bugs, reproduza o comportamento e acrescente regressão adequada ao risco. Atualize primeiro contratos fonte quando o projeto usar geração.
5. Execute as verificações exigidas. Registre comando/procedimento, diretório, resultado e evidência; diferencie código implementado de comportamento validado.
6. Se surgir mudança de requisito/decisão, aplique o controle de mudança da regra comum. Prossiga com trabalho independente e solicite apenas a decisão material ainda ausente.
7. Atualize tarefa e índice. Marque `concluida`/`[x]` somente com implementação e validações atendidas. Em impedimento, registre `bloqueada`, causa, trabalho já feito e próximo passo.
8. Encerre recursos próprios segundo a regra de execução. Quando o usuário pediu uma tarefa, conclua essa entrega; quando pediu continuidade de um conjunto, avance pelas elegíveis dentro desse escopo.

## Saída e conclusão

Informe entrega realizada, verificações, arquivos e pendências reais. Não marque testes não executados como aprovados nem encerre a funcionalidade inteira por concluir uma tarefa.
