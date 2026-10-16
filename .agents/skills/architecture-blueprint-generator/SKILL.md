---
name: architecture-blueprint-generator
description: Gera e atualiza o blueprint arquitetural de um projeto a partir do código, builds, configurações e infraestrutura reais. Use quando for necessário documentar tecnologia, módulos, responsabilidades, dependências, fluxos, padrões, diagramas, decisões e orientações para novas funcionalidades sem inventar detalhes ausentes.
---

# Gerador de blueprint arquitetural

## Objetivo

Produza uma documentação arquitetural baseada em evidências do repositório. O blueprint deve explicar como o sistema funciona hoje, registrar o que é inferência e orientar a evolução sem substituir o código como fonte de verdade.

O documento padrão é:

docs/architecture/Project_Architecture_Blueprint.md

Se o usuário indicar outro caminho, use o caminho solicitado. Não altere código de produção, configurações, regras ArchUnit ou AGENTS.md como efeito colateral.

## Quando usar

Use esta skill para:

- criar o primeiro mapa arquitetural do projeto;
- atualizar o blueprint após mudanças relevantes em módulos, integrações, portas ou infraestrutura;
- explicar a arquitetura para novas pessoas ou orientar a criação de funcionalidades;
- identificar divergências entre documentação, código, build e deployment;
- documentar limites entre microservices, bibliotecas compartilhadas e infraestrutura.

Não use para inventar uma arquitetura ideal, substituir uma revisão de código ou criar decisões que não possam ser sustentadas pelo repositório.

## Descoberta do contexto

Leia antes da análise:

1. AGENTS.md e o perfil local indicado por ele, quando existir;
2. .agents/rules/architecture.md e somente o perfil arquitetural aplicável;
3. .agents/skills/archunit-rules/SKILL.md, somente se o escopo incluir criação ou revisão de validação arquitetural automatizada;
4. .agents/skills/spring-boot-conventions/SKILL.md, quando uma decisão sobre uso de Spring exigir orientação específica;
5. .agents/rules/tests.md e a skill de teste aplicável, quando for preciso avaliar desenho de testes; para descrever a suíte existente, consulte diretamente seus arquivos e build;
6. README.md, quando existir.

Depois, inspecione o código e os arquivos de build. Descubra se o escopo é aplicação única, monorepo, conjunto de microservices, biblioteca ou combinação. Não reutilize módulos, tecnologias ou relações de outro projeto. Confirme sempre se a documentação corresponde ao código atual.

## Fluxo de análise

1. Identifique o escopo: repositório inteiro, módulo ou mudança específica.
2. Determine o tipo de projeto: monorepo, microservices, biblioteca, aplicação única ou combinação.
3. Detecte tecnologias e versões pelos POMs, wrappers, configurações, imports e imagens de infraestrutura.
4. Mapeie módulos e responsabilidades observadas, sem deduzir responsabilidade apenas pelo nome da pasta.
5. Mapeie entradas, saídas, protocolos, contratos, bancos, brokers, provedores externos e arquivos de configuração.
6. Analise dependências entre módulos e direção das dependências internas.
7. Reconheça padrões arquiteturais realmente presentes: hexagonal, layered, event-driven, outbox, gateway, adapters ou outros.
8. Compare a arquitetura observada com as decisões locais e registre divergências.
9. Classifique cada informação como observada, inferida, não confirmada ou recomendada.
10. Gere diagramas somente com relações comprovadas.
11. Valide links, caminhos, versões, portas e comandos contra os arquivos atuais.
12. Escreva ou atualize o blueprint e registre a data da análise e suas limitações.

## Regras de evidência

- Código, build e configuração executáveis comprovam o estado atual; PRD/TechSpec vigentes descrevem o estado desejado. Registre diferenças sem apresentar proposta como implementação ou reescrever requisitos para ocultar defeitos.
- Nunca invente dependências, portas, endpoints, eventos, requisitos de segurança ou motivos de uma decisão.
- Quando uma relação for inferida, marque-a como inferência e indique a evidência.
- Quando a informação não estiver disponível, escreva não evidenciado ou não confirmado; não preencha com suposição.
- Diferencie responsabilidade declarada, comportamento observado e recomendação futura.
- Não exponha segredos, tokens, senhas, chaves ou valores sensíveis no blueprint.
- Não copie arquivos extensos de código. Use pequenos trechos anonimizados somente quando explicarem um padrão arquitetural.
- Não declare que uma arquitetura é hexagonal apenas porque existem pastas chamadas core ou adapters; confirme dependências e responsabilidades.

## Conteúdo mínimo do blueprint

Siga o template em [blueprint-template.md](references/blueprint-template.md). O documento deve conter, quando houver evidência:

- resumo executivo e escopo;
- data, fontes consultadas, versões e grau de confiança;
- mapa de módulos, serviços e componentes compartilhados;
- responsabilidade, tecnologia e ciclo de execução de cada módulo;
- portas, perfis, variáveis externas e dependências de runtime;
- arquitetura interna, camadas, ports, adapters e direção das dependências;
- comunicação síncrona e assíncrona, contratos e eventos;
- dados, persistência, migrations, cache e ownership;
- segurança, autenticação, autorização e limites de exposição;
- tratamento de erros, resiliência, observabilidade e configuração;
- arquitetura de testes e comandos de validação;
- deployment, containers e dependências externas;
- diagramas C4 ou Mermaid coerentes com a implementação;
- padrões de extensão para novas funcionalidades;
- divergências, riscos, dívida arquitetural e recomendações priorizadas;
- perguntas em aberto e gatilhos para atualizar o documento.

## Diagramas

Prefira Mermaid em Markdown quando o repositório não tiver uma ferramenta de diagramas definida. Escolha o menor conjunto que explique o sistema:

- contexto: atores e sistemas externos;
- containers: aplicações, gateways, bancos, brokers, provedores de identidade e outros componentes comprovados;
- componentes: apenas para o módulo relevante;
- sequência ou fluxo: somente para um fluxo crítico.

Rotule relações com protocolo, evento ou responsabilidade quando isso estiver confirmado. Não desenhe classes, fluxos ou dependências que não foram observados. Se não houver evidência suficiente, prefira uma descrição textual.

## Orientação para novas funcionalidades

A seção de evolução deve seguir as convenções locais:

- respeitar `.agents/rules/architecture.md` e o perfil arquitetural selecionado;
- colocar responsabilidades nas camadas, features ou módulos definidos pela arquitetura selecionada;
- introduzir interfaces ou ports somente quando houver fronteira, inversão de dependência ou variação real;
- seguir as convenções Spring Boot somente após verificar a versão do módulo;
- escolher o nível de teste conforme .agents/rules/tests.md, java-junit ou springboot-test;
- criar ou revisar regras ArchUnit quando a fronteira arquitetural mudar;
- atualizar contratos OpenAPI, eventos e configuração quando fizerem parte do fluxo.

Para cada tipo de mudança, informe pontos de entrada, componentes a alterar, dependências permitidas, testes necessários, documentação a atualizar e riscos conhecidos.

## Recursos

- Leia [blueprint-template.md](references/blueprint-template.md) para a estrutura final e o formato de evidências.
- Leia [analysis-checklist.md](references/analysis-checklist.md) durante a descoberta, validação e revisão do blueprint.

