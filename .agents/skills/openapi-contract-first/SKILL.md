---
name: openapi-contract-first
description: Define, valida e evolui contratos OpenAPI versionados e sua geração de código. Use para mudanças em contratos, compatibilidade e código gerado a partir deles; não pressuponha OpenAPI ao trabalhar em qualquer endpoint ou DTO.
---

# Contract-First com OpenAPI

Use esta skill quando uma API for definida por um contrato OpenAPI e esse contrato for a fonte de verdade para documentação, validação, geração e evolução do código. O objetivo é manter o contrato explícito, reproduzível e compatível com a implementação real.

## Regra principal

Altere primeiro o contrato quando o comportamento público da API mudar. Valide-o, regenere o código com a ferramenta e a versão configuradas no projeto e coloque a lógica manual somente nos pontos de extensão suportados. Nunca corrija um arquivo gerado diretamente para esconder uma divergência do contrato.

## Antes de alterar ou gerar

1. Leia `AGENTS.md` e as regras de arquitetura, Spring e testes aplicáveis.
2. Localize o contrato e confirme se o arquivo é `openapi.yaml` ou `openapi.yml`.
3. Leia o `pom.xml`, `build.gradle` ou `build.gradle.kts` do módulo. Identifique a versão do OpenAPI Generator, o gerador, a biblioteca, o diretório de saída, os mapeamentos e as opções de geração.
4. Confirme a versão declarada em `openapi:`. Não use recursos de OpenAPI 3.1 em um contrato 3.0.x sem confirmar suporte no projeto.
5. Inspecione a implementação existente, os arquivos gerados e os testes antes de escolher um novo ponto de extensão.
6. Se houver ambiguidade no contrato, registre a dúvida e peça decisão; não invente campos, status, segurança ou regras de negócio.

## Fluxo obrigatório

1. Analise operações, `operationId`, tags, parâmetros, schemas, respostas, segurança e exemplos.
2. Faça a mudança mínima no contrato, preservando compatibilidade quando possível.
3. Valide o contrato com a ferramenta configurada no projeto. Não use `--skip-validate-spec`, exceto quando houver uma incompatibilidade conhecida e documentada.
4. Regenere pelo ciclo de build versionado, preferencialmente pelo plugin Maven ou Gradle do módulo.
5. Revise o diff gerado: alterações inesperadas em rotas, modelos, imports, configurações ou arquivos auxiliares são sinais para interromper e investigar.
6. Implemente regras de negócio em services, ports e adapters manuais; não edite controllers, DTOs ou interfaces gerados.
7. Atualize testes de contrato, endpoint, unidade ou integração conforme a mudança. Use `java-junit` para lógica sem Spring e `springboot-test` quando o comportamento depender do contexto Spring ou de infraestrutura.
8. Execute validação, testes e build do módulo e relate o contrato, o comando de geração e os arquivos alterados.

## Regras de desenho do contrato

- Dê a cada operação um `operationId` único, estável e expressivo.
- Organize tags por recurso ou contexto de negócio; não use nomes de camadas como tags públicas.
- Reutilize estruturas com `components` e `$ref`, evitando schemas, parâmetros e respostas duplicados.
- Declare parâmetros de rota como obrigatórios e explicite limites, formatos e padrões quando forem parte do contrato.
- Diferencie campo ausente, nulo, vazio e valor padrão; confirme como a versão do OpenAPI e o gerador representam cada caso.
- Documente respostas de sucesso e falhas esperadas com status, conteúdo e schema. Use um formato de erro consistente e seguro.
- Declare esquemas de segurança e suas aplicações no nível global ou da operação. A documentação não pode prometer autenticação que o código não aplica.
- Use exemplos válidos, pequenos e sem credenciais, tokens, dados pessoais ou hosts internos.
- Prefira mudanças aditivas. Remover operações, renomear propriedades, tornar campos obrigatórios ou alterar semântica de enums pode quebrar consumidores; trate isso como decisão de compatibilidade e migração.
- Não coloque lógica de negócio, segredo ou comportamento implícito no contrato.

## Geração e código manual

O código gerado é um artefato do contrato e da configuração do build. Mantenha a versão do gerador fixada, use os mesmos templates e mapeamentos no CI e não misture geração por CLI com outra versão sem intenção explícita.

Em projetos com `delegatePattern`, o controller e as representações permanecem gerados; a implementação manual deve estar no delegate ou adapter previsto pelo módulo. Em outros projetos, localize o ponto de extensão equivalente antes de criar código novo. Não crie uma arquitetura genérica apenas porque o prompt original sugere controllers, services e repositories: siga a arquitetura existente.

## Contexto do módulo

Registre, a partir do build e do contrato analisados: caminho da especificação, versão OpenAPI, ferramenta e versão de geração, gerador/biblioteca, diretório de saída, mapeamentos, sufixos e ponto de extensão manual. Não copie esses valores de outro módulo ou projeto.

## Recursos de consulta

- [Diretrizes detalhadas e checklist](references/contract-guidelines.md)
- [Exemplos de contrato, build, geração e implementação](references/examples.md)
- [Especificação OpenAPI](https://spec.openapis.org/oas/v3.1.0)
- [Uso do OpenAPI Generator](https://openapi-generator.tech/docs/usage/)
- [Gerador Spring](https://openapi-generator.tech/docs/generators/spring/)
- [Plugins Maven e Gradle](https://openapi-generator.tech/docs/plugins/)
- [Customização do OpenAPI Generator](https://openapi-generator.tech/docs/customization/)
