---
name: avaliar-arquitetura
description: Compara alternativas técnicas, documenta ganhos e perdas e avalia quando registrar uma decisão em ADR. Use na descoberta do PRD, no desenho da TechSpec ou ao reavaliar uma decisão arquitetural.
---

# Avaliar arquitetura

## Comparar alternativas

1. Formule a decisão e indique o que ela pode mudar no escopo, no aceite ou na implementação. Vincule requisitos e restrições aplicáveis. Reuse o contexto confirmado e as decisões vigentes.
2. Compare alternativas plausíveis, incluindo manter a abordagem atual ou entregar em etapas quando isso atender à necessidade. Registre quais restrições eliminam opções. Se houver só uma abordagem plausível e nenhum trade-off material, registre a justificativa em uma linha.
3. Use [o template da matriz](references/TEMPLATE_TRADEOFFS.md) com critérios comuns a todas as opções. Selecione as dimensões que diferenciam as alternativas, como compatibilidade, dados, segurança, desempenho, esforço de entrega, operação e reversibilidade. Especifique quem ou o que é afetado, ganhos, perdas e sustentação de cada comparação. Use estimativas qualitativas e identifique incertezas. Use números somente com fonte ou método explícito.
4. Recomende uma opção quando houver evidência suficiente. Explique qual ganho justifica o custo assumido, quais mitigações e verificações serão necessárias e em que condição rever a escolha. Se a decisão ficar pendente, indique a evidência necessária e a etapa que pode resolvê-la.

No SDD, registre a matriz no `blueprint.md` da funcionalidade e referencie-a na TechSpec. Em um PRD legado sem blueprint, a TechSpec dispõe da própria seção de decisões para registrar a comparação. Durante o PRD, leve ao documento de produto apenas os efeitos sobre escopo, restrições ou aceite. Defina na TechSpec as escolhas de implementação e as verificações decorrentes. Em análises avulsas, use o destino solicitado ou entregue a comparação na resposta.

## Decidir se cabe um ADR

Um ADR preserva o motivo de uma decisão que precisa continuar compreensível após a funcionalidade. Avalie a necessidade quando a escolha estabelecer um padrão para outras equipes ou funcionalidades, alterar fronteiras ou ownership de dados, criar um compromisso público ou operacional duradouro ou exigir uma migração difícil de reverter. Para uma escolha local e facilmente reversível, a justificativa na TechSpec é suficiente.

- Procure um ADR vigente para a mesma decisão e referencie-o. Se a decisão mudar, preserve o histórico e relacione os registros anterior e sucessor.
- Registre junto à análise se cabe ADR, com o motivo e o link quando existir. Use um [ADR](references/TEMPLATE_ADR.md) por decisão suficientemente formulada. Se faltar evidência do desenho, indique o que a TechSpec deve resolver antes de registrar a decisão.
- Siga a convenção do projeto. Na ausência dela, use `docs/architecture/adr/NNNN-<slug>.md` e atribua um número ainda livre. Comece como `proposto` quando a escolha for uma recomendação. Use `aceito` com a origem da decisão registrada, inclusive quando ela tiver sido delegada dentro do escopo autorizado. Preserve propostas rejeitadas quando explicarem por que uma abordagem foi abandonada. Marque o registro anterior como `substituído` quando o sucessor for aceito.
- Vincule o ADR aceito ao desenho e às verificações que protegem a decisão, quando aplicáveis. Referencie a matriz de origem e sintetize apenas os trade-offs que explicam a escolha.
