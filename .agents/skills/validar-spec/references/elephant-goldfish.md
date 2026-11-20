# Revisão independente da especificação

Adaptação do [Elephant-Goldfish Model](https://github.com/google/elephant-goldfish-model) ao relatório `spec-review.md` do kit. O autor (Elephant) tem o contexto de trabalho. O revisor (Goldfish) avalia se os artefatos sustentam as mesmas decisões sem receber o histórico da conversa.

1. **Defina o alvo.** Para requisitos de alto impacto ainda em definição, revise o PRD e o blueprint. Para liberar implementação, use as revisões vigentes do PRD, blueprint, TechSpec, tarefas e ADRs pertinentes. Identifique a etapa e os IDs afetados. Em revisão parcial, inclua as restrições transversais pertinentes. Libere a implementação inteira somente com a cobertura exigida para essa etapa.
2. **Delegue uma passagem.** Inicie um único revisor em contexto novo, sem herdar a conversa, em modo somente leitura. Envie objetivo, etapa, caminhos das specs e fontes pertinentes, restrições transversais e o roteiro abaixo. Preserve conclusões anteriores, hipóteses do autor e propostas de correção fora da entrada do revisor. O revisor pode buscar evidência adicional nos trechos pertinentes do projeto. Se o ambiente não oferecer contexto novo, registre a revisão independente como `NÃO EXECUTADO` e use a validação local quando o escopo permitir. Se a independência for requisito da liberação, deixe a etapa `BLOQUEADO` com a causa.
3. **Consolide no lugar da revisão semântica local desse escopo.** Confira cobertura, referências e dependências. Valide nas fontes os achados do revisor, agrupe duplicatas e aplique o critério de prontidão da etapa. Registre o escopo, revisões, modo, achados confirmados, falsos positivos relevantes e veredito em `spec-review.md`. A decisão e as correções autorizadas cabem ao agente principal, com registro da origem de cada decisão.
4. **Reavalie mudanças.** Após correções, confira os IDs e decisões modificados, consumidores e verificações dependentes. Amplie a revisão quando a mudança alterar escopo, aceite ou fronteira transversal. Use outra passagem independente somente se o pedido ou a convenção aplicável exigir essa validação para a mudança.

## Roteiro para o revisor

- Reconstrua em poucas linhas o comportamento e as restrições que um implementador precisa respeitar.
- Identifique interpretações plausíveis que levem a resultados diferentes, obrigações sem cobertura e divergências com fontes ou decisões vigentes. Para cada achado, indique ID/trecho, evidência, consequência para a etapa e a pergunta ou correção que o resolve.
- Quando a funcionalidade adotar um perfil arquitetural, confira se o desenho explica as responsabilidades e a direção das dependências e prevê como verificar as fronteiras.
- Distinga pendências que impedem a etapa de melhorias opcionais. Sinalize premissas que o texto deixa em aberto. Emita a recomendação de prontidão para o escopo avaliado.

Para avaliar eficiência, compare com a revisão local sobre as mesmas revisões e registre achados válidos, falsos positivos, tempo e tokens de autor, revisor, consolidação e correções quando disponíveis. O [roteiro de avaliação](../../../../docs/sdd-validation.md) define a comparação.
