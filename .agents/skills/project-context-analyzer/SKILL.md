---
name: project-context-analyzer
description: Analisa projetos por evidências de código, build, configuração e infraestrutura e, quando solicitado ou adotado localmente, registra contexto versionado e atualiza o blueprint macro. Use em onboarding, greenfield, brownfield e evolução entre módulos.
---

# Analisador de contexto e evolução arquitetural

## Objetivo

Produza um contexto arquitetural verificável. Aplique [project-analysis.md](../../rules/project-analysis.md) para delimitar a pergunta, o escopo e o destino do resultado. Reuse contexto já validado; descoberta local simples não exige este diagnóstico amplo.

Os caminhos abaixo são padrões somente quando o respectivo artefato for solicitado ou adotado; produzir um deles não exige produzir o outro:

1. `docs/context/<projeto_analisado>/contexto.<NN>.md`: release versionada do contexto específico, com evidências, estado atual, lacunas e mapa de melhorias.
2. `docs/architecture/Project_Architecture_Blueprint.md`: visão macro do repositório, seus módulos, comunicação, dependências externas e fronteiras.

Não implemente melhorias de código como efeito colateral de uma análise. Se o usuário pedir implementação, use as evidências como base e atualize a documentação adotada após validar as mudanças.

## Quando aplicar

Use esta skill quando o pedido envolver:

- análise de um projeto inteiro, módulo, microservice ou componente compartilhado;
- criação ou atualização de `docs/context`;
- diagnóstico de arquitetura, acoplamento, coesão, comunicação ou ownership;
- documentação para onboarding ou planejamento de melhorias;
- atualização do blueprint após alterações em contratos, portas, eventos, bancos, infraestrutura ou deployment;
- análise de projeto greenfield ou brownfield.

Não use para uma revisão isolada de código, teste ou contrato quando não houver necessidade de registrar contexto e visão arquitetural. Nesses casos, encaminhe para a skill específica.

## Entradas obrigatórias e prioridade de evidências

Leia somente as fontes pertinentes ao escopo, quando existirem; não releia material já conferido e ainda válido:

1. `AGENTS.md` e as regras locais relevantes;
2. `README.md`, documentação de execução e decisões existentes;
3. arquivos de build e dependências (`pom.xml`, `build.gradle`, `package.json`, wrappers e lockfiles);
4. código-fonte, testes, contratos, configurações e infraestrutura;
5. histórico ou contexto anterior apenas para entender evolução, nunca para substituir o estado atual.

Para descrever o estado atual, priorize comportamento executável, build/dependências, configuração, infraestrutura e testes sobre documentação antiga. Especificações vigentes definem a mudança pretendida; divergência do código pode ser um defeito, não motivo para apagar o requisito. Registre o conflito e sua origem.

Consulte [analysis-workflow.md](references/analysis-workflow.md) quando precisar detalhar a investigação. Leia [context-release-template.md](references/context-release-template.md) somente ao produzir uma release e [macro-blueprint-template.md](references/macro-blueprint-template.md) somente ao produzir o blueprint. Use [examples.md](references/examples.md) apenas para esclarecer uma dúvida de representação.

## Fluxo de trabalho

1. Defina o escopo real: repositório, módulo, microservice ou componente. Use o nome do diretório/projeto como `<projeto_analisado>` e confirme se há mais de um módulo relevante.
2. Detecte tecnologias e versões reais pelos arquivos de build, imports, plugins, imagens e configurações. Não assuma versões pelo nome da tecnologia.
3. Mapeie responsabilidades, entradas, saídas, persistência, brokers, APIs, eventos, autenticação, armazenamento e dependências externas.
4. Reconstrua as relações de comunicação: origem, destino, protocolo/canal, contrato, sincronismo, ownership, retry/idempotência e evidência.
5. Analise direção de dependências, fronteiras, coesão, acoplamento, riscos e divergências entre o planejado e o implementado.
6. Classifique cada afirmação como `observado`, `inferido`, `não confirmado` ou `recomendado`. Recomendações não devem ser descritas como comportamento existente.
7. Escolha o destino pela regra de análise. Crie a próxima release somente se o registro versionado de contexto tiver sido solicitado ou adotado e houver diagnóstico novo relevante; preserve histórico. Atualizar perfil, TechSpec ou resposta não exige release.
8. Crie o blueprint quando solicitado ou exigido pela convenção local; atualize um existente somente quando o escopo o afetar. Preserve informações válidas.
9. Valide links, caminhos, nomes de módulos, portas, contratos e diagramas. Execute testes/build somente quando forem seguros e relevantes; registre comandos e limitações.
10. Encerre ao responder a pergunta e delimitar impactos e pendências. Confira somente o template do artefato produzido, quando houver; não crie entregáveis adicionais para satisfazer um checklist.

## Greenfield e brownfield

### Brownfield

Descreva primeiro o que existe. Separe claramente dívida, divergência e recomendação. Não proponha uma migração total quando uma evolução incremental ou uma regra de compatibilidade for mais segura. Inclua impacto, prioridade, dependências, critério de conclusão e risco de cada melhoria relevante.

### Greenfield

Não invente implementação. Documente decisões propostas como `recomendadas`, explicite premissas e registre perguntas que precisam de decisão. O blueprint pode conter arquitetura-alvo, mas deve separar visão pretendida de componentes, portas e integrações já comprovados.

## Comunicação entre projetos e serviços

O blueprint macro deve conter uma matriz de comunicação e, quando houver evidência suficiente, um diagrama Mermaid de contexto/container. Para cada relação, registre:

- origem e destino;
- responsabilidade de cada lado e ownership dos dados;
- protocolo/canal e direção;
- contrato ou evento usado;
- síncrono ou assíncrono;
- autenticação, timeout, retry, idempotência e dead letter quando evidenciados;
- ambiente/porta apenas quando confirmado;
- classificação da evidência.

Não desenhe comunicação apenas porque dois módulos existem no mesmo repositório. Dependências compartilhadas, chamadas indiretas e integrações planejadas devem ser identificadas como tais.

## Uso eficiente de contexto

Prefira leitura seletiva e resumo semântico para reduzir contexto. Preserve integralmente código, contratos, comandos e evidências cuja redação exata seja necessária.

Para detalhes de geração do blueprint, consulte [architecture-blueprint-generator](../architecture-blueprint-generator/SKILL.md). Para regras de fronteira e dependências, consulte [archunit-rules](../archunit-rules/SKILL.md) apenas quando a análise exigir validação arquitetural automatizada.

## Critérios de qualidade

Confira os critérios aplicáveis ao resultado solicitado:

- se houver release de contexto, ela tiver número, data, escopo, fontes e base da versão anterior;
- responsabilidades e comunicação estiverem separadas de recomendações;
- riscos e melhorias tiverem prioridade, evidência, impacto e próximo passo;
- se houver blueprint criado/alterado, ele refletir os serviços e componentes compartilhados relevantes;
- os diagramas e matrizes não contiverem relações inventadas;
- segredos, tokens, credenciais e dados pessoais desnecessários não aparecerem na documentação;
- limitações, dúvidas e comandos não executados estiverem explícitos.

