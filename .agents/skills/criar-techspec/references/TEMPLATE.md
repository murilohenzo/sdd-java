# TechSpec — [funcionalidade]

- Estado: [rascunho | pronto | desatualizado]
- Revisão: [número]
- Atualizado em: [AAAA-MM-DD]
- PRD de origem: [prd.md](prd.md), revisão [número]

## Contexto confirmado

[Arquivos consultados, módulos, versões efetivas, arquitetura observada, configuração e limitações. Diferencie estado atual de escolhas para greenfield.]

## Solução e decisões

[Abordagem, responsabilidades, fluxo e justificativas. Referencie RF/RNF/CA; registre alternativas somente para decisões relevantes.]

## Componentes e arquivos

- [Caminho existente ou proposto]: [responsabilidade, mudança e consumidores impactados].

[Um diagrama pequeno é opcional quando esclarecer o fluxo.]

## Contratos, dados e compatibilidade

[Somente quando aplicável: entradas/saídas, validação, erros, autorização, persistência e eventos. Referencie schema/OpenAPI versionado, operationId ou tipo; não duplique o contrato inteiro. Para contrato novo ainda não existente, indique destino e conteúdo proposto.]

[Considere migração, compatibilidade dos consumidores, transações, idempotência e falhas conforme o risco real. Não imponha campos nulos, envelope de erro ou padrões de outro projeto.]

## Verificação e rastreabilidade

Para cada caso:

- ID: [TU-01 | TI-01 | TC-01 | E2E-01 | MAN-01].
- Critérios: [CA-*].
- Cenário e dados: [precondições, ação e casos relevantes].
- Resultado esperado: [observável e verificável].
- Nível, ferramenta e local previsto: [menor nível suficiente].
- Execução: [comando e diretório confirmados, ou procedimento manual].
- Dependências: [ambiente/serviços necessários ou nenhuma].

[Todos os CA precisam de verificação; não é obrigatório utilizar todos os níveis. Para novo build, explicite comandos propostos e a tarefa que os disponibilizará.]

## Entrega e operação

[Sequência de construção, dependências, observabilidade, configuração e implantação/reversão quando a mudança exigir. Não adicione infraestrutura por padrão.]

## Riscos e pendências

[Impacto, mitigação, decisão necessária e etapa bloqueada, ou nenhuma pendência material.]

## Regras e skills aplicáveis

[Liste somente documentos pertinentes e decisões que afetam a solução. Registre desvios e justificativas; não use uma declaração genérica de leitura de todas as regras.]

## Alterações desta revisão

[Motivo/origem, decisões alteradas e impacto em tarefas, contratos e verificações.]
