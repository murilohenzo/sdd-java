---
name: executar-qa
description: Valida uma funcionalidade implementada contra os critérios de aceitação, com evidências, regressões e relatório de QA. Abrange API, eventos, jobs ou UI conforme o produto; não é planejamento nem revisão estrutural de código.
---

# Executar QA

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md), [execução e evidências](../../rules/execution.md) e [o template](references/TEMPLATE.md). Em `tasks/prd-<slug>/`, use PRD, TechSpec e índice de tarefas vigentes. Abra detalhes das tarefas quando necessários para entender a entrega e evidências.

## Procedimento

1. Percorra `RF/RNF → CA → verificação → tarefa → evidência` para todas as obrigações vigentes. Confira tarefas necessárias, escopo implementado, revisões de origem, ambiente e pré-requisitos; aceite ausente não pode ser presumido a partir de um checkbox.
2. Prepare somente os serviços necessários e registre código/configuração testados. Para API, mensageria, batch ou biblioteca, valide interfaces e efeitos observáveis com as ferramentas adequadas; não exija uma página inicial.
3. Execute verificações de unidade, integração, contrato, E2E ou manuais previstas, conforme o nível necessário. Para UI, use navegador disponível e inclua acessibilidade, responsividade e estados relevantes quando fizerem parte do aceite.
4. Registre cada CA e caso como `PASSOU`, `FALHOU`, `BLOQUEADO` ou `NÃO EXECUTADO`, com resultado esperado, observado e evidência. Recurso opcional sem aplicação pode ser `NÃO APLICÁVEL` com justificativa; isso não dispensa um CA vigente.
5. Investigue falhas com logs, respostas, eventos, persistência ou estado da interface pertinentes. Em QA com estabilização autorizada, corrija bugs no escopo e adicione regressão proporcional; se o pedido for somente validar, registre-os sem alterar o produto.
6. Reexecute os casos afetados após correção. Se uma tentativa não gerar nova evidência ou houver impedimento externo, registre o bloqueio e o próximo passo; não repita indefinidamente a mesma ação.
7. Salve `qa.md` com defeitos, lacunas de cobertura, correções quando realizadas, testes e evidências em `evidences/` quando necessário. Se a validação mostrar que uma conclusão era incorreta, reabra a tarefa; se faltar uma entrega, acrescente-a ao plano somente quando o pedido autorizar corrigir/estabilizar, preservando o vínculo ao PRD. Depois revalide os casos afetados.
8. Encerre somente recursos próprios.

## Saída e conclusão

Use `APROVADO` somente com todas as obrigações vigentes rastreadas, tarefas do plano vigente concluídas, CA e checks exigidos atendidos nas revisões avaliadas e sem defeitos bloqueantes; `REPROVADO` quando houver falha bloqueante comprovada; `BLOQUEADO` quando faltar validação obrigatória e não houver falha bloqueante comprovada. Entregue caminho, veredito e limitações; screenshot isolada não comprova o fluxo completo.
