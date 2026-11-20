---
name: executar-task
description: Implementa ou retoma tarefas de uma funcionalidade com PRD, TechSpec e plano existentes, validando o resultado e atualizando o progresso. Não use para pedidos restritos a planejamento, revisão ou QA.
---

# Executar tarefa

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md) e [execução e evidências](../../rules/execution.md). Resolva a funcionalidade em `tasks/prd-<slug>/` e comece por `tasks.md` e pela tarefa selecionada.

## Procedimento

1. Use a tarefa explicitamente indicada. Em retomada, priorize a tarefa em andamento identificada pelo contexto ou pelo registro de próxima ação; reavalie seu bloqueio, se houver. Sem alvo definido, selecione a próxima pendente cujas dependências estejam concluídas. Se houver mais de uma retomada plausível, esclareça o alvo; se nenhuma tarefa for elegível, informe a cadeia de bloqueio. Não pule uma dependência por ela vir depois no arquivo.
2. Siga os CA, as referências da TechSpec e as verificações da tarefa. Inclua as restrições e as decisões transversais que regem a mudança. Em retomada, confronte a base registrada com o estado real do código e das specs. Confira clareza do aceite, cobertura dos casos e contradições materiais antes de implementar.
3. Carregue as regras e skills técnicas do trecho afetado. Consulte o blueprint e os ADRs vinculados quando a tarefa tocar as fronteiras ou compromissos descritos. Confirme versões, pontos de extensão e comandos no projeto. Inicie somente os serviços necessários à validação.
4. Registre `em_andamento`. Implemente as subtarefas no escopo solicitado; para bugs, reproduza o comportamento e acrescente regressão adequada ao risco. Atualize primeiro contratos fonte quando o projeto usar geração.
5. Execute as verificações exigidas. Registre comando/procedimento, diretório, resultado e evidência; diferencie código implementado de comportamento validado.
6. Se surgir uma evidência que mude os trade-offs ou uma decisão arquitetural, use [avaliar-arquitetura](../avaliar-arquitetura/SKILL.md) e aplique o controle de mudança da regra comum. Prossiga com trabalho independente e solicite apenas a decisão material ainda ausente.
7. Atualize tarefa e índice. Marque `concluida`/`[x]` somente com implementação e validações atendidas. Em impedimento, registre `bloqueada`, causa, trabalho já feito e próximo passo.
8. Encerre recursos próprios segundo a regra de execução. Quando o usuário pediu uma tarefa, conclua essa entrega; quando pediu continuidade de um conjunto, avance pelas elegíveis dentro desse escopo.

## Saída e conclusão

Informe entrega realizada, verificações, arquivos e pendências reais. Não marque testes não executados como aprovados nem encerre a funcionalidade inteira por concluir uma tarefa.
