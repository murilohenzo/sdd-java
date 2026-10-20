---
name: criar-prd
description: Cria ou atualiza PRD com problema, escopo, requisitos e critérios de aceitação verificáveis. Use para definir uma funcionalidade antes do desenho técnico; não para escrever TechSpec ou implementar.
---

# Criar PRD

## Entrada e leitura

Use a descrição do usuário e o contexto do produto disponível. Leia [o contrato SDD](../../rules/spec-driven-development.md), o perfil local quando existir e [o template](references/TEMPLATE.md). Identifique o slug e preserve um PRD existente ao atualizá-lo.

## Procedimento

1. Separe problema, público, resultado desejado, escopo e restrições. Explore somente fontes que esclareçam o domínio e o comportamento atual.
2. Pergunte apenas por lacunas que mudem requisitos ou aceite. Não substitua decisões internas do produto por pesquisa na web. Registre premissas reversíveis e pendências materiais explicitamente.
3. Redija `tasks/prd-<slug>/prd.md` com RF/RNF e CA estáveis. Inclua cenários relevantes de sucesso, erro e limite; descreva resultado observável. Não invente metas, integrações ou telas.
4. Verifique que cada requisito tem aceite verificável e que não há contradição com exclusões e restrições. Preserve decisões do usuário, distinguindo-as de propostas.
5. Defina revisão, data e estado segundo o contrato SDD. Ao alterar requisitos existentes, identifique os artefatos dependentes que precisam de reconciliação.

## Saída e conclusão

Entregue o PRD salvo e informe estado, principais pendências e caminho. Só marque `pronto` com o critério de prontidão atendido. Não implemente como efeito colateral de um pedido de PRD; prossiga para outra etapa apenas se já fizer parte do pedido.
