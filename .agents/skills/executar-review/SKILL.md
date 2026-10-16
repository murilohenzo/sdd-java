---
name: executar-review
description: Revisa código quanto a bugs, regras aplicáveis, TechSpec e tarefas, com evidências e veredito. Use para revisão de código; corrija quando o pedido incluir correção ou estabilização. Não substitui QA funcional.
---

# Executar revisão

## Entrada e leitura

Leia [o contrato SDD](../../rules/spec-driven-development.md), [execução e evidências](../../rules/execution.md) e [o template](references/TEMPLATE.md). Resolva o escopo da revisão: diff/base ou arquivos solicitados, funcionalidade e estado do código.

No fluxo SDD, use `techspec.md`, `tasks.md` e os CA relevantes do `prd.md`. Para revisão avulsa autorizada sem esses documentos, declare a limitação e revise o código disponível; não fabrique specs.

## Procedimento

1. Confirme a base do diff e preserve alterações preexistentes. Leia regras e skills pertinentes aos arquivos revisados; não carregue todas as regras por rotina.
2. Procure problemas verificáveis de correção, regressão, segurança, contratos e limites arquiteturais. Diferencie defeito, divergência de especificação e preferência opcional.
3. Confira tarefas marcadas completas contra implementação, testes e evidências. Relacione cada achado a arquivo/linha, cenário, impacto e regra/CA/decisão pertinente.
4. Execute checks obrigatórios e testes relevantes usando o projeto. Evidências anteriores só valem se ainda corresponderem ao código/configuração revisados.
5. Se o pedido incluir correções, corrija dentro do escopo e revalide os caminhos afetados. Pedido restrito a revisão produz achados. Mudança material de escopo ou requisito segue a regra SDD.
6. Salve `codereview.md` na pasta da funcionalidade; numa revisão avulsa, use o destino pedido ou entregue o relatório na resposta.
7. Defina veredito: `APROVADO` se checks e aderência exigidos passaram; `APROVADO COM RESSALVAS` somente para melhorias não bloqueantes com checks aprovados; `REPROVADO` para defeito bloqueante confirmado; `BLOQUEADO` se faltar evidência obrigatória sem defeito bloqueante já comprovado.
8. Encerre recursos próprios conforme a regra de execução.

## Saída e conclusão

Entregue veredito, achados ordenados por impacto, validações e limitações. Aprovação de código não significa que todos os CA da funcionalidade foram validados em QA.
