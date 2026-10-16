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

Preparação: mudança de API com dois consumidores; perfil parcialmente desatualizado; projeto não adota releases de contexto.
Pedido: “Crie a TechSpec desta mudança.”
Esperado: investigar o fluxo e consumidores afetados, conferir os fatos relevantes e registrar o desenho na TechSpec. Não analisar serviços independentes nem criar release ou blueprint como efeito colateral.

## 15. Atualização de contexto após implementação

Preparação: mudança validada altera um comando local documentado no perfil; blueprint existente não é afetado.
Pedido: “Atualize a documentação afetada pela entrega.”
Esperado: corrigir o comando no perfil e manter referências consistentes; não gerar uma release de contexto, reescrever o blueprint ou carregar templates sem necessidade.

## Registro de validação desta revisão

Caso adicional de retomada: uma tarefa está em_andamento e a conversa/índice a identifica como trabalho atual. Ao pedir “continue”, o agente deve conferir seu estado e retomá-la, sem abandoná-la para escolher apenas tarefas pendentes. Um bloqueio resolvido exige reavaliação, não conclusão automática.

Em 2026-10-16, o kit foi revisado por inspeção das instruções e templates para cobrir estes cenários. A validação automatizada estrutural é executada com os comandos do [perfil local](../.agents/project/profile.md). Este registro não declara benchmark de qualidade, redução de tokens ou teste independente por outro agente.

Resultados desta revisão:

- Validador estrutural: 56 documentos, 16 skills e 130 links locais examinados, sem erros. Os links de saída dos templates seguem a limitação descrita no README.
- Testes do validador: 10 casos aprovados. No ambiente desta revisão, a criação das fixtures temporárias exigiu execução fora do sandbox.
- Validador oficial de skills (`quick_validate.py` de `skill-creator`): 16 skills aprovadas. A dependência PyYAML foi usada em diretório temporário, sem adicioná-la ao kit.

A confirmação prática restante é executar uma funcionalidade real no repositório de destino, com seu build, ambiente e critérios de aceitação.
