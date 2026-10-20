# Guia de trabalho — Java, Spring Boot e SDD

Este arquivo é o ponto de entrada. Use o perfil para fatos locais, rules para restrições e skills para procedimentos; carregue apenas o necessário para o pedido.

## Contexto e fontes de verdade

1. Leia `.agents/project/profile.md`, quando existir, e confira suas evidências no repositório. Perfil copiado ou desatualizado não comprova módulos, versões ou comandos.
2. Para mudanças de software, identifique módulos, versões efetivas de Java/Spring/plugins, modelo de execução e dependências nos builds, wrappers e configurações relacionados ao escopo. Se não existirem, registre a ausência; não invente uma aplicação.
3. Código, build, testes e configuração comprovam o **estado atual**. PRD e TechSpec vigentes definem o **estado desejado da mudança autorizada**. Não altere requisitos para legitimar um bug, nem trate uma proposta como já implementada.
4. Classifique informação relevante como observada, inferida, proposta ou pendente. Em conflito material, registre a divergência e resolva a decisão antes de executar o trecho afetado.

## Fluxo de especificação

Para trabalho orientado por especificação, leia [spec-driven-development.md](.agents/rules/spec-driven-development.md). Os artefatos ficam em `tasks/prd-<slug>/`; não carregue outras funcionalidades sem necessidade.

Em uma entrega SDD completa, siga PRD → TechSpec → tarefas → checagem de consistência → implementação → review → QA → reconciliação final com o PRD. Se a checagem ou a validação revelar uma lacuna, atualize a fonte correta, retome o trabalho afetado e revalide; não declare a funcionalidade concluída por haver documentos ou tarefas marcadas.

- Problema, escopo e critérios de aceitação: [criar-prd](.agents/skills/criar-prd/SKILL.md).
- Solução técnica derivada do PRD: [criar-techspec](.agents/skills/criar-techspec/SKILL.md).
- Entregas e dependências derivadas da TechSpec: [criar-tasks](.agents/skills/criar-tasks/SKILL.md).
- Ambiguidades, lacunas e rastreabilidade: [validar-spec](.agents/skills/validar-spec/SKILL.md).
- Implementar ou continuar uma tarefa: [executar-task](.agents/skills/executar-task/SKILL.md).
- Revisar conformidade do código: [executar-review](.agents/skills/executar-review/SKILL.md).
- Validar comportamento e critérios de aceitação: [executar-qa](.agents/skills/executar-qa/SKILL.md).
- Preparar mensagem ou criar commit quando solicitado: [criar-commit](.agents/skills/criar-commit/SKILL.md), reutilizando o contexto da entrega.

Execute somente as etapas abrangidas pelo pedido. Uma correção pequena ou edição documental independente não exige criar um conjunto artificial de PRD, TechSpec e tarefas; se já houver especificação afetada, mantenha-a consistente.

## Regras e skills por escopo

- Código Java: [code-standards.md](.agents/rules/code-standards.md).
- Pacotes, módulos ou dependências: [architecture.md](.agents/rules/architecture.md); aplique apenas o perfil adotado. Para validação automatizada, use [archunit-rules](.agents/skills/archunit-rules/SKILL.md).
- Código/configuração Spring: [spring-boot-conventions](.agents/skills/spring-boot-conventions/SKILL.md).
- Testes: [tests.md](.agents/rules/tests.md), com [java-junit](.agents/skills/java-junit/SKILL.md) para Java puro ou [springboot-test](.agents/skills/springboot-test/SKILL.md) quando depender de Spring/infraestrutura.
- Contratos OpenAPI e geração: [openapi-contract-first](.agents/skills/openapi-contract-first/SKILL.md).
- Descoberta de contexto ou impacto entre módulos: [project-analysis.md](.agents/rules/project-analysis.md); a regra delimita a análise e indica quando carregar skills de diagnóstico ou blueprint.
- Execução de comandos, serviços e evidências: [execution.md](.agents/rules/execution.md).

Referências e exemplos são consultados sob demanda. Os arquivos em `.agents/rules/` são documentos locais explicitamente roteados por este guia, não uma configuração de carregamento automático. Templates não instituem arquitetura nem dependências.

## Limites e autonomia

- Respeite as instruções aplicáveis e o escopo autorizado pelo usuário. Reutilize decisões já dadas; pergunte apenas por informações materiais ainda ausentes.
- Preserve alterações preexistentes, contratos públicos e limites entre módulos. Não introduza abstrações, bibliotecas ou migrações sem necessidade demonstrada.
- Não edite código gerado: altere a fonte e use o gerador configurado. Não misture modelos bloqueantes e reativos sem verificar a fronteira.
- Mantenha segredos e dados sensíveis fora de código, fixtures, logs e documentação. Não execute exclusões amplas de dados, volumes ou ambientes.
- Use ferramentas disponíveis no ambiente; nenhuma skill pressupõe um agente, navegador ou ferramenta de perguntas pelo nome.
- Preserve a redação precisa de requisitos, contratos e evidências. [caveman](.agents/skills/caveman/SKILL.md) é estilo solicitado, não otimização automática de especificações.

## Conclusão

Entregue o resultado, arquivos alterados, validações executadas e limitações. Só marque tarefa concluída com os critérios e verificações exigidos atendidos; teste não executado não equivale a teste aprovado. Atualize os artefatos afetados sem reescrever histórico válido.

Ao reutilizar este kit, recrie o perfil com [profile.example.md](.agents/project/profile.example.md). Comandos de manutenção estão no perfil; instruções de adoção estão no [README](README.md).
