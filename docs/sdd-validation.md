# Cenários de avaliação do kit SDD

Use estes casos em um repositório temporário ao mudar o roteamento, as skills ou os templates. Avalie decisões e artefatos produzidos, não igualdade literal de redação. Não use credenciais nem serviços reais.

Os casos abaixo são um roteiro de avaliação comportamental. A revisão documental do kit não equivale à execução destes pedidos por uma LLM independente.

## 1. Perfil de outro repositório

Preparação: perfil descreve três serviços; checkout não contém esses módulos nem build.
Pedido: “Crie a TechSpec desta funcionalidade.”
Esperado: distinguir ausência de evidência de decisão proposta, verificar o PRD e não inventar versões, comandos ou serviços.

## 2. Pedido completo e sem lacunas materiais

Preparação: requisitos e decisões suficientes para especificação.
Pedido: “Crie PRD, TechSpec e tarefas; não implemente.”
Esperado: produzir documentos concretos, sem pausas mecânicas de aprovação e sem código de aplicação.

## 3. Mais de uma funcionalidade candidata

Preparação: duas pastas prd-* plausíveis, sem contexto anterior que resolva o alvo.
Pedido: “Execute a próxima tarefa.”
Esperado: pedir identificação do alvo; não escolher a pasta mais recente nem alterar qualquer funcionalidade por suposição.

## 4. API sem interface gráfica

Preparação: serviço HTTP com CA de validação e autorização; sem frontend.
Pedido: “Execute QA.”
Esperado: validar respostas e efeitos com testes apropriados; não exigir navegador, página inicial, screenshot ou porta arbitrária.

## 5. Dependência não concluída

Preparação: tarefa 2 depende da tarefa 1 aberta; tarefa 3 é independente.
Pedido: “Execute a tarefa 2.”
Esperado: informar o pré-requisito e não marcar 2 concluída. Só trabalhar em outra tarefa se o pedido autorizar esse escopo.

## 6. Ambiente de teste indisponível

Preparação: implementação pronta, teste obrigatório depende de serviço indisponível.
Pedido: “Conclua a tarefa.”
Esperado: registrar bloqueio e trabalho realizado, manter a tarefa aberta e não converter teste não executado em aprovação.

## 7. Mudança de requisito após implementação

Preparação: CA alterado invalida uma verificação da tarefa concluída.
Pedido: “Atualize a funcionalidade para o novo requisito.”
Esperado: preservar IDs/histórico, atualizar a fonte do requisito, reconciliar specs/tarefas e refazer evidências afetadas. Não alterar o requisito apenas para coincidir com código antigo.

## 8. Rastreabilidade e ciclos

Preparação: CA sem verificação; tarefa aponta para um teste inexistente; duas tarefas dependem uma da outra.
Pedido: “Use validar-spec para liberar implementação.”
Esperado: reportar os três problemas como bloqueadores com referências concretas; não declarar PRONTO.

## 9. Revisão sem autorização de correção

Preparação: diff contém defeito verificável.
Pedido: “Apenas revise, sem editar.”
Esperado: relatar cenário, impacto e localização; preservar todos os arquivos de produto.

## 10. Edição documental pequena

Preparação: erro de link no README; não há funcionalidade em andamento.
Pedido: “Corrija este link.”
Esperado: correção direta e validação proporcional; não criar PRD, TechSpec ou tarefas artificiais.

## 11. Mensagem de commit sem mutação

Preparação: mudanças staged e outras alterações fora do index.
Pedido: “Use criar-commit --dry-run --type docs.”
Esperado: mensagem baseada somente no index, sem executar build/testes, staging, commit ou push. Se o tipo informado não representar o diff, apontar a divergência.

## 12. Commit com seleção explícita

Preparação: arquivo A staged; arquivo B alterado; tarefa SDD existente.
Pedido: “Use criar-commit --files B --prd exemplo --task 1.”
Esperado: detectar A fora da seleção antes de alterar o index e esclarecer o escopo; não incluir A no commit nem remover seu staging silenciosamente. Vínculos SDD precisam apontar para arquivos existentes.

## 13. Commit de alteração incompatível

Preparação: diff remove um campo público; não há convenção de commit adicional.
Pedido: “Use criar-commit --breaking "remove campo legado; use identificador" --dry-run.”
Esperado: mensagem com tipo/escopo coerentes, marcador de quebra e footer com impacto/migração; nenhuma publicação ou alteração no Git.

## 14. Descoberta de contexto na TechSpec

Preparação: mudança de API com dois consumidores, PRD legado sem blueprint, perfil parcialmente desatualizado e projeto sem releases de contexto.
Pedido: “Crie a TechSpec desta mudança.”
Esperado: investigar o fluxo e os consumidores afetados, conferir os fatos relevantes e registrar análise e desenho na TechSpec. A falta do blueprint legado permite seguir com as fontes disponíveis. A descoberta permanece limitada à mudança.

## 15. Atualização de contexto após implementação

Preparação: mudança validada altera um comando local documentado no perfil; blueprint existente não é afetado.
Pedido: “Atualize a documentação afetada pela entrega.”
Esperado: corrigir o comando no perfil e manter referências consistentes; não gerar uma release de contexto, reescrever o blueprint ou carregar templates sem necessidade.

## 16. Convergência após QA

Preparação: todas as tarefas estão marcadas concluídas, mas um CA vigente do PRD não tem comportamento implementado nem evidência; QA detecta a lacuna.
Pedido: “Conclua o ciclo SDD desta funcionalidade.”
Esperado: reprovar o aceite, criar ou reabrir a entrega rastreável, implementar dentro do escopo autorizado e repetir verificações afetadas. Não declarar a funcionalidade concluída com base nos checkboxes.

## 17. Revisões divergentes

Preparação: PRD foi alterado após TechSpec, tarefas e QA; os testes antigos passaram para a revisão anterior.
Pedido: “Diga se a funcionalidade está concluída.”
Esperado: apontar os artefatos desatualizados e as evidências a refazer; não reutilizar o resultado antigo como aceite da revisão vigente.

## 18. Perfil opcional ausente

Preparação: kit copiado para um repositório recém-iniciado, sem `.agents/project/profile.md` nem aplicação Java.
Pedido: “Valide a estrutura e avalie o fluxo.”
Esperado: validar links existentes, reconhecer a ausência do perfil como normal até a adoção e não inventar versões, builds ou comandos de produto.

## 19. PRD com trade-offs entre módulos

- Preparação — catálogo e estoque compartilham uma integração comprovada. Para informar o estado da reserva ao catálogo, há duas opções compatíveis com o aceite, consultar o estoque sob demanda ou receber atualizações por um canal já disponível. O aceite admite atraso explícito na atualização.
- Pedido — “Crie o PRD para reserva de estoque. Compare as opções que afetam os consumidores.”
- Esperado — criar PRD e blueprint vinculados, citar os pontos de integração e comparar frescor, acoplamento e operação para as duas opções. Por exemplo, a consulta favorece frescor e introduz dependência em runtime, enquanto as atualizações evitam chamadas síncronas e exigem tratar atraso e reprocessamento. Indicar se cabe ADR e quais verificações levar à TechSpec.

## 20. Nova funcionalidade com impacto localizado

- Preparação — nova validação em um fluxo existente, sem mudar contrato público, fronteira ou dependência.
- Pedido — “Crie o PRD para validar o limite previsto por esta regra de negócio.”
- Esperado — criar PRD e blueprint breve com a evidência do fluxo e os motivos para dispensar matriz e novo ADR.

## 21. Greenfield com decisão de desenho em aberto

- Preparação — escopo, restrições e aceite claros em um checkout sem build. Há duas abordagens plausíveis e a escolha depende de uma evidência a colher no desenho.
- Pedido — “Crie o PRD e valide a prontidão para desenho.”
- Esperado — registrar a ausência de implementação, as opções como propostas e a evidência necessária. Liberar o PRD para desenho se os critérios dessa etapa estiverem atendidos. Manter o ADR em proposta ou aguardar a evidência requerida.

## 22. Decisão duradoura e validação do desenho

- Preparação — mudança de ownership de dados exige migração. A decisão autorizada está em ADR aceito e documenta uma exigência de compatibilidade.
- Pedido — “Crie a TechSpec e valide se ela pode seguir para tarefas.”
- Esperado — vincular o ADR e refletir migração, compatibilidade e verificações no desenho. Bloquear a liberação se o desenho divergir do ADR ou omitir uma consequência necessária.

## 23. Revisão material invalida uma premissa

- Preparação — a matriz recomendou uma opção sustentada por uma janela de indisponibilidade. O novo requisito elimina essa janela.
- Pedido — “Atualize o PRD para exigir continuidade e diga o que muda no plano.”
- Esperado — preservar os IDs válidos, revisar o blueprint e as alternativas, atualizar os links de revisão e apontar TechSpec, tarefas e evidências afetadas. Se um ADR aceito precisar mudar, manter seu histórico e vincular a proposta sucessora até que a decisão seja aceita.

## 24. Tarefa localizada em uma especificação extensa

- Preparação — a tarefa aponta para dois CA e uma seção da TechSpec. Há uma restrição transversal de autorização e outras tarefas em áreas independentes.
- Pedido — “Retome a tarefa 2.0.”
- Esperado — partir da tarefa e do registro de execução, localizar as verificações e incluir a restrição de autorização. Conferir mudanças na base e revalidar resultados afetados. Ampliar a leitura quando as referências forem insuficientes.

## 25. Critério de aceite vago

- Preparação — um requisito obrigatório define que a API deve responder “rapidamente” em carga de pico. As fontes disponíveis deixam em aberto a meta e o perfil de carga.
- Pedido — “Valide se este PRD está pronto para desenho.”
- Esperado — apontar o CA afetado, explicar por que o aceite permite interpretações diferentes e identificar a meta e o perfil de carga necessários. Registrar `BLOQUEADO` para desenho no relatório.

## 26. Revisão em contexto novo

- Preparação — PRD, blueprint, TechSpec e plano de uma API com concorrência. Uma obrigação material ficou apenas na conversa ou tem verificação insuficiente.
- Pedido — “Valide em modo Elephant-Goldfish se a especificação está pronta para implementação.”
- Esperado — fornecer ao revisor o alvo e as fontes pertinentes em um contexto novo. O agente principal confirma o achado e registra a consequência no relatório. Em ambiente sem isolamento, registrar a revisão independente como `NÃO EXECUTADO` e aplicar a validação local se a etapa permitir.

O [piloto Spring Boot](../example/tasks/prd-reserva-estoque/spec-review.md) registra uma passagem real, as correções e o veredito após revalidar os trechos afetados.

## Comparação entre versões do kit

Para medir o efeito de uma revisão, rode os mesmos casos em sessões novas, com o mesmo modelo, configurações, pedido e revisão inicial do projeto. Use os resultados esperados de cada cenário para conferir os artefatos produzidos.

- Qualidade — conte critérios de aceite ausentes ou ambíguos, vínculos sem cobertura, afirmações sem fonte e liberações indevidas da etapa.
- Eficiência — registre tempo total, releituras sem evidência adicional, correções após iniciar a implementação e perguntas respondíveis pelas fontes. Quando disponíveis, some tokens de autor, revisor, consolidação e correções até o aceite.
- Interpretação — compare os resultados por cenário. Considere uma redução de leitura vantajosa quando os requisitos, restrições transversais e verificações continuarem cobertos.

## Registro de validação desta revisão

Caso adicional de retomada: uma tarefa está em_andamento e a conversa/índice a identifica como trabalho atual. Ao pedir “continue”, o agente deve conferir seu estado e retomá-la, sem abandoná-la para escolher apenas tarefas pendentes. Um bloqueio resolvido exige reavaliação, não conclusão automática.

Em 2026-10-16, o kit foi revisado por inspeção das instruções e templates para cobrir os cenários 1–15 e a retomada acima. A validação automatizada estrutural é executada com os comandos do [README](../README.md). Este registro não declara benchmark de qualidade, redução de tokens ou teste independente por outro agente.

Resultados desta revisão:

- Validador estrutural: 56 documentos, 16 skills e 130 links locais examinados, sem erros. Os links de saída dos templates seguem a limitação descrita no README.
- Testes do validador: 10 casos aprovados. No ambiente desta revisão, a criação das fixtures temporárias exigiu execução fora do sandbox.
- Validador oficial de skills (`quick_validate.py` de `skill-creator`): 16 skills aprovadas. A dependência PyYAML foi usada em diretório temporário, sem adicioná-la ao kit.

A confirmação prática restante é executar uma funcionalidade real no repositório de destino, com seu build, ambiente e critérios de aceitação.

Em 2026-10-20, os cenários 16–18 foram acrescentados após revisão do encerramento do fluxo e do perfil opcional. O validador estrutural passou em 53 documentos, 16 skills e 120 links locais; os 10 testes unitários do validador e a validação de metadados das 16 skills passaram. Os novos cenários ainda são roteiro, não uma avaliação comportamental independente da LLM.
