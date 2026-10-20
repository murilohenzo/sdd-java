---
name: spec-driven-development
description: Contrato comum do fluxo PRD, TechSpec, tarefas, implementação, review e QA.
---

# Contrato do fluxo SDD

Leia esta regra ao criar, validar ou executar uma especificação. Ela concentra as convenções compartilhadas; as skills detalham cada etapa.

## Seleção e contexto mínimo

- Resolva a funcionalidade pelo caminho/slug informado ou pelo contexto inequívoco da conversa. Sem indicação, use a única pasta candidata em `tasks/prd-*/`; se houver várias candidatas plausíveis, pergunte qual é o alvo. Não escolha pela data de modificação.
- Caminhos de artefatos são relativos à raiz do repositório. Referências de uma skill são relativas ao seu `SKILL.md`. O texto `--prd <slug>` é uma convenção de entrada, não um comando executável obrigatório.
- Leia perfil, artefatos de entrada da etapa e somente regras, fontes e skills pertinentes. Durante a execução, use `tasks.md` como índice e abra a tarefa selecionada, suas dependências e as seções relevantes das specs.
- Se faltar um pré-requisito, informe o caminho exato. Produza-o se o pedido já abranger essa etapa; caso contrário, não fabrique o documento nem prossiga com trabalho dependente. Continue o que for independente.
- Perguntas devem resolver escopo, aceite, segurança, compatibilidade ou decisão técnica material. Para escolhas reversíveis de baixo impacto, registre uma premissa. Regras internas de negócio vêm do usuário e das fontes do projeto; pesquisa externa fornece referências, não decisões do produto.

## Responsabilidade dos artefatos

- `prd.md`: problema, usuários, escopo, requisitos funcionais (`RF-*`), não funcionais (`RNF-*`) e critérios observáveis (`CA-*`).
- `techspec.md`: solução proposta, evidências do estado atual, decisões, contratos, riscos e casos de verificação (`TU-*`, `TI-*`, `TC-*`, `E2E-*` ou `MAN-*`). Use somente os níveis necessários; `MAN-*` identifica uma verificação manual reproduzível.
- `tasks.md`: índice, dependências e mapa de cobertura `RF/RNF → CA → verificação → tarefa`. É a fonte do estado global das tarefas.
- `task_<n>.md`: escopo de uma entrega, subtarefas, referências e evidências de execução. Não copie a TechSpec.
- `spec-review.md`, `codereview.md`, `qa.md`: resultados de validação de especificação, código e comportamento, respectivamente. Crie cada relatório quando sua etapa for executada.
- `evidences/`: saídas necessárias para reproduzir uma conclusão, sem segredos. Não crie pasta vazia; screenshots são úteis apenas para fluxos visuais.

Preserve IDs existentes e não os renumere por estética. Novos IDs devem ser únicos dentro da funcionalidade. Um requisito pode ter vários critérios e verificações; toda obrigação no escopo deve ser rastreada. Tarefas habilitadoras podem não verificar um CA diretamente, desde que indiquem qual entrega viabilizam.

## Prontidão e autorização

Documentos de especificação usam `rascunho`, `pronto` ou `desatualizado`, revisão e data. `pronto` significa que os critérios abaixo foram verificados pelo agente; não significa aprovação humana. Registre decisões do usuário somente quando realmente dadas. O pedido define quais etapas estão autorizadas; não crie pausas de aprovação mecânicas entre etapas já solicitadas.

1. **PRD pronto para desenho:** problema/escopo claros, RF/RNF verificáveis, CA associados e nenhuma pendência material que impeça o desenho. Metas não fornecidas permanecem pendentes, sem números inventados.
2. **TechSpec pronta para tarefas:** PRD de referência identificado, componentes e integrações localizados ou explicitamente propostos, decisões justificadas e cada CA ligado a uma verificação com resultado esperado.
3. **Tarefas prontas para execução:** cobertura completa, dependências existentes e sem ciclos, entregas delimitadas, comandos/ambiente de validação identificados e ausência de bloqueadores do escopo selecionado.
4. **Tarefa concluída:** implementação presente, critérios da entrega satisfeitos e verificações exigidas aprovadas com evidências. Mudanças apenas escritas ou testes bloqueados mantêm a tarefa aberta.
5. **Funcionalidade validada:** todas as obrigações vigentes do PRD percorrem `RF/RNF → CA → verificação → tarefa → evidência` no código/configuração avaliados; tarefas do plano vigente concluídas, CA e checks exigidos atendidos, sem defeitos bloqueantes, specs coerentes e etapas exigidas sem veredito bloqueado ou reprovado. Não confunda conclusão de uma tarefa com aceite de toda a funcionalidade.

Antes de implementar, verifique ambiguidades, contradições, cobertura e dependências. Use `validar-spec` para uma análise dedicada; o checklist pode ser feito na própria execução sem criar outro relatório obrigatório. Não existe certificação automática de prontidão por mera presença dos arquivos.

Ao encerrar uma entrega SDD completa, reconcilie o resultado de review e QA com o PRD e o plano: confira também referências sem origem, CA sem evidência atual e trabalho ainda aberto. Se houver obrigação não atendida, corrija dentro do escopo autorizado, reabra a tarefa cuja conclusão foi invalidada ou acrescente uma entrega rastreável, e repita somente as verificações afetadas. Um pedido restrito a review ou QA registra a lacuna e o próximo passo sem presumir autorização para implementar.

## Estados e evidências

- Tarefas: `pendente`, `em_andamento`, `bloqueada`, `concluida`. Checkboxes `[x]` são reservados a `concluida`; mantenha índice e arquivo da tarefa sincronizados.
- Verificações: `PASSOU`, `FALHOU`, `BLOQUEADO`, `NÃO EXECUTADO` ou `NÃO APLICÁVEL`, este último com motivo. Não descarte um CA usando `NÃO APLICÁVEL` sem mudança explícita de escopo.
- Para cada execução, registre comando/procedimento, diretório, revisão ou estado do código, resultado observado e evidência mínima. Não invente contagens de testes ou cobertura.
- Relatórios devem distinguir falha do produto, limitação do ambiente e melhoria opcional. Sem validação obrigatória, o veredito é `BLOQUEADO`; com falha comprovada bloqueante, é `REPROVADO`, mesmo que existam também impedimentos de ambiente.

## Mudanças e retomada

Quando um requisito ou decisão mudar, registre motivo e origem, atualize primeiro o artefato que o define e propague o impacto para contratos, testes e tarefas. Marque artefatos dependentes como `desatualizado` até reconciliá-los. Reabra tarefas concluídas somente se seus critérios deixarem de estar satisfeitos; registre quais evidências precisam ser refeitas. Não reescreva o PRD para esconder uma implementação incorreta.

Em retomadas, confira o estado real do repositório e das dependências. Registre na tarefa o que foi feito, o bloqueio, o próximo passo e recursos iniciados. Reuse verificações anteriores apenas se a revisão, configuração e escopo ainda as sustentarem.

Quando o pedido incluir commit, use [criar-commit](../skills/criar-commit/SKILL.md) após a entrega, reutilizando diff e evidências vigentes. O vínculo `--prd <slug> --task <n>` é opcional; commit não substitui aceite nem dispara publicação.

## Proporcionalidade

Templates são contratos de conteúdo mínimo, não formulários a preencher com texto genérico. Remova seções opcionais sem aplicação ou registre brevemente o motivo. Para funcionalidade pequena, mantenha os documentos curtos; não crie contrato duplicado, ADR, research, blueprint, UI ou infraestrutura sem necessidade. Faça descobertas com arquivos/ferramentas disponíveis, sem pressupor delegação ou acesso à web.
