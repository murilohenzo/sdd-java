---
name: spec-driven-development
description: Contrato do fluxo SDD, da descoberta arquitetural no PRD à validação da entrega.
---

# Contrato do fluxo SDD

Leia esta regra ao criar, validar ou executar uma especificação. Ela concentra as convenções compartilhadas; as skills detalham cada etapa.

## Seleção e contexto mínimo

- Resolva a funcionalidade pelo caminho/slug informado ou pelo contexto inequívoco da conversa. Sem indicação, use a única pasta candidata em `tasks/prd-*/`; se houver várias candidatas plausíveis, pergunte qual é o alvo. Não escolha pela data de modificação.
- Caminhos de artefatos são relativos à raiz do repositório. Referências de uma skill são relativas ao seu `SKILL.md`. O texto `--prd <slug>` é uma convenção de entrada, não um comando executável obrigatório.
- Comece pelas entradas da etapa e siga os links/IDs até as seções que controlam a entrega. Na execução, use `tasks.md` para selecionar a tarefa e localizar dependências. Leia os CA, a solução e as verificações referenciadas, incluindo restrições transversais. Amplie a leitura se uma referência faltar ou se o código divergir da especificação. Na validação da funcionalidade inteira, confira todos os requisitos vigentes.
- Se faltar um pré-requisito, informe o caminho exato. Produza-o se o pedido já abranger essa etapa; caso contrário, não fabrique o documento nem prossiga com trabalho dependente. Continue o que for independente.
- Trate uma lacuna como bloqueadora da etapa se ela impedir definir, entregar ou verificar uma obrigação vigente daquela etapa. Indique o ID ou trecho afetado, a consequência e a decisão ou evidência necessária. Encaminhe dúvidas de desenho para a TechSpec quando o aceite já estiver definido. Para escolhas reversíveis de baixo impacto, registre uma premissa e avance. Pergunte quando as fontes disponíveis e a autorização já dada forem insuficientes para resolver uma lacuna material.

## Responsabilidade dos artefatos

- `prd.md`: problema, usuários, escopo, requisitos funcionais (`RF-*`), não funcionais (`RNF-*`) e critérios observáveis (`CA-*`).
- `blueprint.md`: contexto arquitetural e impacto da funcionalidade. Nasce com o PRD, cita o estado observado, identifica fronteiras afetadas, registra a matriz de trade-offs materiais e indica se cabe ADR. Referencia o blueprint macro quando houver.
- `techspec.md`: desenho técnico, contratos, decisões locais, riscos e casos de verificação (`TU-*`, `TI-*`, `TC-*`, `E2E-*` ou `MAN-*`). Usa o blueprint e os ADRs aplicáveis como entradas. `MAN-*` identifica uma verificação manual reproduzível.
- **ADR (Architecture Decision Record)**: decisão arquitetural duradoura, motivo, consequências e condições de revisão. Use a convenção do projeto ou `docs/architecture/adr/NNNN-<slug>.md`. O blueprint e a TechSpec vinculam o registro aplicável.
- `tasks.md`: índice, dependências e mapa de cobertura `RF/RNF → CA → verificação → tarefa`. É a fonte do estado global das tarefas.
- `task_<n>.md`: escopo de uma entrega, subtarefas, referências e evidências de execução. Não copie a TechSpec.
- `spec-review.md`, `codereview.md`, `qa.md`: resultados de validação de especificação, código e comportamento, respectivamente. Crie cada relatório quando sua etapa for executada.
- `evidences/`: saídas necessárias para reproduzir uma conclusão, sem segredos. Não crie pasta vazia; screenshots são úteis apenas para fluxos visuais.

Preserve IDs existentes e não os renumere por estética. Novos IDs devem ser únicos dentro da funcionalidade. Um requisito pode ter vários critérios e verificações; toda obrigação no escopo deve ser rastreada. Tarefas habilitadoras podem não verificar um CA diretamente, desde que indiquem qual entrega viabilizam.

Crie ou reconcilie `blueprint.md` ao criar um PRD ou alterar materialmente seu escopo, requisitos ou restrições arquiteturais. Se a funcionalidade tiver impacto localizado, descreva em poucas linhas a fronteira afetada e o motivo da ausência de trade-offs materiais ou de novo ADR. Uma edição textual pode manter a revisão analisada no blueprint se escopo, requisitos e restrições continuarem equivalentes. Para PRDs anteriores a essa convenção, a TechSpec e a execução podem seguir com as evidências disponíveis até uma revisão material do PRD.

## Prontidão e autorização

PRD, TechSpec e plano usam os estados `rascunho`, `pronto` e `desatualizado`, além de revisão e data. O estado `pronto` registra a checagem dos critérios abaixo. Registre a autoria das decisões conforme a fonte. O pedido define quais etapas estão autorizadas; não crie pausas de aprovação mecânicas entre etapas já solicitadas.

1. **PRD pronto para desenho** quando problema e escopo estiverem claros, RF/RNF tiverem CA verificáveis e o blueprint exigido estiver vinculado e coerente. Resolva pendências que impeçam fixar escopo, restrições ou aceite. Decisões que dependam do desenho podem seguir para a TechSpec com a lacuna explícita.
2. **TechSpec pronta para tarefas** quando identificar a revisão do PRD e descrever os componentes e integrações observados ou propostos. Resolva pendências de desenho que afetam o plano, justifique decisões materiais com base nas evidências e nos ADRs aplicáveis e ligue cada CA a uma verificação com resultado esperado.
3. **Tarefas prontas para execução:** cobertura completa, dependências existentes e sem ciclos, entregas delimitadas, comandos/ambiente de validação identificados e ausência de bloqueadores do escopo selecionado.
4. **Tarefa concluída:** implementação presente, critérios da entrega satisfeitos e verificações exigidas aprovadas com evidências. Mudanças apenas escritas ou testes bloqueados mantêm a tarefa aberta.
5. **Funcionalidade validada:** todas as obrigações vigentes do PRD percorrem `RF/RNF → CA → verificação → tarefa → evidência` no código/configuração avaliados; tarefas do plano vigente concluídas, CA e checks exigidos atendidos, sem defeitos bloqueantes, specs coerentes e etapas exigidas sem veredito bloqueado ou reprovado. Não confunda conclusão de uma tarefa com aceite de toda a funcionalidade.

Antes de implementar, verifique ambiguidades, contradições, cobertura e dependências. Use `validar-spec` para uma análise dedicada; o checklist pode ser feito na própria execução sem criar outro relatório obrigatório. Não existe certificação automática de prontidão por mera presença dos arquivos.

Quando solicitada ou autorizada por convenção local, a revisão independente do [validar-spec](../skills/validar-spec/SKILL.md) pode assumir a checagem de compreensão da especificação no escopo avaliado. Use uma passagem em contexto novo e consolide achados no mesmo relatório. Preserve a revisão de código e o QA do ciclo.

Ao encerrar uma entrega SDD completa, reconcilie review e QA com o PRD e o plano. Confira referências sem origem, CA sem evidência atual e trabalho em aberto. Verifique também se mudanças materiais no desenho foram refletidas no blueprint e nas decisões vinculadas. Se houver obrigação pendente, corrija dentro do escopo autorizado, reabra a tarefa afetada ou acrescente uma entrega rastreável e repita as verificações pertinentes. Um pedido restrito a review ou QA registra a lacuna e o próximo passo.

## Estados e evidências

- Tarefas: `pendente`, `em_andamento`, `bloqueada`, `concluida`. Checkboxes `[x]` são reservados a `concluida`; mantenha índice e arquivo da tarefa sincronizados.
- Verificações: `PASSOU`, `FALHOU`, `BLOQUEADO`, `NÃO EXECUTADO` ou `NÃO APLICÁVEL`, este último com motivo. Não descarte um CA usando `NÃO APLICÁVEL` sem mudança explícita de escopo.
- Para cada execução, registre comando/procedimento, diretório, revisão ou estado do código, resultado observado e evidência mínima. Não invente contagens de testes ou cobertura.
- Relatórios devem distinguir falha do produto, limitação do ambiente e melhoria opcional. Sem validação obrigatória, o veredito é `BLOQUEADO`; com falha comprovada bloqueante, é `REPROVADO`, mesmo que existam também impedimentos de ambiente.

## Mudanças e retomada

Quando um requisito ou decisão mudar, registre motivo e origem. Atualize primeiro a fonte da mudança: PRD para requisitos, TechSpec para desenho ou ADR para decisão arquitetural registrada. Revise o impacto no blueprint, nas demais especificações e nos contratos, testes e tarefas afetados. Marque as especificações dependentes como `desatualizado` até reconciliá-las. Se uma premissa do blueprint perder validade, registre a revisão do PRD que foi analisada e o ponto que precisa ser atualizado. Reabra tarefas concluídas somente se seus critérios deixarem de estar satisfeitos e indique as evidências a refazer.

Em retomadas, comece pelo registro de execução da tarefa. Compare o estado local do código, contratos e configuração relevantes e os requisitos e verificações referenciados com a base registrada. Uma revisão nova pede avaliar o que mudou e quais resultados dependem disso. Reuse evidências cuja base ainda seja válida e repita as verificações afetadas. Registre trabalho feito, bloqueio, próximo passo e recursos iniciados.

Quando o pedido incluir commit, use [criar-commit](../skills/criar-commit/SKILL.md) após a entrega, reutilizando diff e evidências vigentes. O vínculo `--prd <slug> --task <n>` é opcional; commit não substitui aceite nem dispara publicação.

## Proporcionalidade

Use os templates para conferir o conteúdo necessário e remova seções opcionais sem aplicação. Mantenha o blueprint da funcionalidade proporcional ao impacto. Blueprint macro, release de contexto e ADR seguem seus próprios gatilhos. Referencie documentos e contratos vigentes para evitar duplicação.
