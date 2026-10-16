# Diretrizes de Contract-First/OpenAPI

## Fonte de verdade e ciclo de vida

O arquivo OpenAPI versionado descreve a interface pública: rotas, métodos, parâmetros, payloads, respostas e segurança. Código gerado, documentação e testes derivados devem ser atualizados a partir dele. A implementação pode impor regras adicionais internas, mas não deve contradizer o contrato publicado.

Antes de editar, identifique:

- versão OpenAPI e dialeto de schema suportado;
- módulo proprietário do contrato e consumidores conhecidos;
- plugin, versão do gerador, templates, mappings e diretório de saída;
- convenções de nomes, erros, autenticação, paginação e versionamento já adotadas;
- diferença entre arquivos gerados, código manual e arquivos de configuração.

## Qualidade do contrato

Use esta revisão antes da geração:

- `openapi`, `info.title`, `info.version` e descrição refletem o serviço real;
- cada operação tem `operationId` único, tag coerente e descrição suficiente;
- cada parâmetro de rota aparece na URL e tem `required: true`;
- limites, `pattern`, `format`, enumerações e obrigatoriedade expressam garantias reais;
- respostas de sucesso e falhas esperadas possuem status, media type e schema;
- schemas compartilhados estão em `components.schemas` e são referenciados por `$ref`;
- exemplos passam pelo próprio schema e não contêm segredos ou dados reais;
- `securitySchemes` e requisitos de segurança correspondem à configuração da aplicação;
- não há endpoints duplicados, nomes ambíguos ou propriedades sem semântica definida.

Não trate `format` como validação de negócio suficiente. Validação de entrada deve existir também na borda da aplicação, usando as capacidades reais do framework e do gerador.

## Evolução e compatibilidade

Mudanças normalmente aditivas, como um novo endpoint ou uma propriedade opcional, tendem a ser mais compatíveis. Avalie como potencialmente incompatíveis:

- remover ou renomear rota, parâmetro, schema ou propriedade;
- tornar obrigatório um campo antes opcional;
- restringir valores, tamanho ou formato aceitos;
- mudar tipo, semântica ou significado de um enum;
- retirar uma resposta ou alterar um status esperado;
- mudar autenticação, autorização ou formato de erro.

Para uma mudança incompatível, defina estratégia de versão, período de compatibilidade, migração e comunicação aos consumidores. Não silencie a quebra apenas ajustando o código gerado.

## Geração reproduzível

Prefira o plugin do build com a versão fixada no repositório. A geração deve ser repetível em uma máquina limpa e no CI. Registre opções importantes, como `generatorName`, `library`, `delegatePattern`, `apiPackage`, `modelPackage`, mappings e arquivos suportados.

Se o resultado exigir template customizado, explique por que a configuração padrão não basta e versione o template junto do projeto. Não edite `target/` ou outro diretório gerado como correção permanente.

Depois de gerar:

1. verifique o diff e a lista de arquivos;
2. confirme que nenhuma rota ou modelo desapareceu por erro de configuração;
3. compile o módulo;
4. execute testes dos endpoints alterados;
5. confirme que a implementação manual ainda implementa a interface gerada.

## Greenfield e brownfield

Em greenfield, use o contrato para alinhar consumidores, segurança, erros, paginação e exemplos antes de criar a implementação. Gere apenas o que o projeto realmente precisa e escolha a arquitetura a partir das convenções do framework.

Em brownfield, preserve compatibilidade e faça primeiro um inventário do contrato atual, dos consumidores e dos pontos gerados. Não reorganize pacotes, troque gerador ou migre OpenAPI 3.0 para 3.1 no mesmo diff sem necessidade explícita. Uma melhoria arquitetural deve ser separada da mudança de contrato quando isso reduzir risco.

## Testes e automação

Inclua no CI, conforme as ferramentas disponíveis:

- lint e validação sintática do contrato;
- checagem de breaking changes contra a versão publicada;
- geração e compilação do código;
- testes de contrato ou de endpoint para status, payload, erros e segurança;
- verificação de que arquivos gerados não foram editados manualmente.

O comando de validação do OpenAPI Generator é uma opção quando a versão da CLI é compatível, por exemplo `openapi-generator-cli validate -i contrato.yaml`. No projeto, dê preferência ao plugin Maven ou Gradle já configurado para evitar divergência de versões.

## Práticas que evitam geração frágil

- Evite `Map<String, Object>` quando um schema nomeado comunica um contrato estável.
- Evite nomes que colidam com tipos, palavras reservadas ou convenções do gerador.
- Defina explicitamente formatos de data, hora, identificadores e valores monetários.
- Não use respostas genéricas para ocultar erros que consumidores precisam tratar.
- Mantenha o contrato pequeno e orientado à API; detalhes de persistência não devem vazar para schemas públicos.
- Prefira nomes que expressem intenção de negócio, não nomes de tabelas ou classes internas.
- Se a ferramenta produzir um resultado inesperado, verifique primeiro versão, configuração, mappings e template antes de alterar o código gerado.

## Referências oficiais

- [OpenAPI Specification 3.1](https://spec.openapis.org/oas/v3.1.0)
- [OpenAPI Generator: Usage](https://openapi-generator.tech/docs/usage/)
- [OpenAPI Generator: Spring](https://openapi-generator.tech/docs/generators/spring/)
- [OpenAPI Generator: Plugins](https://openapi-generator.tech/docs/plugins/)
- [OpenAPI Generator: Customization](https://openapi-generator.tech/docs/customization/)
- [Swagger: API design best practices](https://swagger.io/blog/api-design-best-practices/)
