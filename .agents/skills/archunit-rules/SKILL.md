---
name: archunit-rules
description: Analisa e cria regras ArchUnit para validar decisões arquiteturais de aplicações Java. Use ao revisar pacotes, módulos, camadas, features, dependências, ciclos ou isolamento em projetos greenfield e brownfield.
---

# ArchUnit Rules — análise arquitetural executável

## Objetivo e fonte de verdade

Converta decisões arquiteturais comprovadas em testes ArchUnit claros, pequenos e executáveis. Leia [architecture.md](../../rules/architecture.md) e o perfil local; não crie uma arquitetura paralela na suíte.

Use esta skill para criar ou revisar regras de dependência, detectar ciclos, avaliar a arquitetura atual e preparar validações para execução local e CI.

## Roteamento para os recursos

1. Leia primeiro [architecture.md](../../rules/architecture.md) e somente o perfil arquitetural adotado pelo projeto.
2. Leia [architectural-analysis.md](references/architectural-analysis.md) ao analisar o estado atual, escolher o escopo ou tratar legado.
3. Leia [examples.md](references/examples.md) quando precisar escrever ou adaptar exemplos Java, propriedades de freeze ou regras específicas.
4. Consulte [.agents/rules/tests.md](../../rules/tests.md) para convenções gerais de testes.

Os exemplos foram separados para manter esta instrução principal curta e permitir consulta progressiva.

## Descoberta do módulo

Antes de editar código, inspecione o módulo alvo:

1. o pom.xml ou build.gradle e a versão real do ArchUnit, se houver;
2. a classe principal Spring Boot e o pacote raiz;
3. a árvore efetiva de pacotes e os testes existentes;
4. archunit.properties, stores de freeze e configurações de CI;
5. a implementação atual, não apenas a estrutura desejada.

Não presuma que ArchUnit já está configurado nem que o projeto usa uma arquitetura específica. Não adicione dependências quando o pedido for apenas de análise; quando a implementação for solicitada, use uma versão compatível com o build e com a versão efetiva do JUnit.

## Fluxo de análise e implementação

1. Classifique o contexto como greenfield, brownfield ou transição.
2. Delimite o módulo e o pacote raiz analisados.
3. Compare a arquitetura documentada com a arquitetura efetivamente encontrada.
4. Liste dependências permitidas, proibidas, ciclos e violações existentes.
5. Separe regras obrigatórias, recomendadas e ainda não aplicáveis.
6. Escolha a menor regra que expresse cada decisão: camadas, pacotes, módulos, slices ou dependências específicas.
7. Use um nome de teste e uma mensagem de falha que expliquem a decisão quebrada.
8. Confirme as assinaturas da API na versão real do ArchUnit.
9. Execute o teste do módulo e, quando aplicável, a verificação usada no CI.
10. Relate premissas, violações preexistentes, migrações necessárias e limitações.

## Seleção do contrato arquitetural

Proteja somente decisões documentadas e observáveis. Exemplos possíveis, dependendo do projeto:

- camadas respeitam a direção declarada;
- módulos ou features dependem apenas de APIs publicadas;
- domínio não conhece frameworks quando houver isolamento definido;
- adapters e infraestrutura não vazam para o core em arquitetura hexagonal;
- pacotes internos não são acessados por outros contextos;
- módulos não formam ciclos;
- testes não devem ser importados como produção.

Use layeredArchitecture() para direção entre camadas, slices() ou módulos para ciclos e regras de dependência específicas para isolamento. Não trate consideringOnlyDependenciesInLayers() como isolamento completo: ele restringe apenas as camadas declaradas.

## Segurança da regra

- Use a classe principal Spring Boot como âncora e ImportOption.DoNotIncludeTests.
- Prefira obter o pacote raiz por Application.class.getPackageName() a codificar nomes completos.
- Não use padrões amplos que incluam módulos ou testes por acidente.
- Não use allowEmptyShould(true) para esconder pacotes ou classes ausentes.
- Não suponha que um pacote vazio seja observável: ArchUnit analisa classes compiladas.
- Valide anotações diretamente quando a decisão for sobre anotações.
- Não congele regras novas de um projeto greenfield.
- Em brownfield, use freeze somente como baseline explícito e temporário para violações legadas; nunca para esconder uma regra nova ou uma decisão de segurança.
- Não proíba bibliotecas, anotações ou tecnologias sem confirmar que a convenção pertence ao módulo analisado.
- Não transforme uma regra de estilo opcional em bloqueio arquitetural sem decisão registrada.

## Greenfield e brownfield

### Greenfield

Defina o contrato arquitetural antes da implementação, aplique regras estritas desde o início e faça a suíte falhar para qualquer violação. Use regras pequenas e necessárias; não crie uma lista especulativa de proibições.

### Brownfield

Produza primeiro um inventário das violações e diferencie dívida existente de regressão nova. Preserve o comportamento atual enquanto cria um caminho de migração. Se solicitado, aplique FreezingArchRule apenas às regras legadas escolhidas, versione o store e mantenha a criação ou atualização acidental desabilitada no CI.

O freeze é uma ferramenta de transição, não uma justificativa para diminuir o limite arquitetural. Quando uma violação antiga for corrigida, remova-a do baseline e registre a evolução.

## Formato da suíte

- Use o nome de teste já adotado pelo módulo; na ausência de padrão, ArchitectureRulesTest.
- Prefira JUnit 5 e regras declarativas.
- Use campos @ArchTest static final ArchRule para regras estáticas.
- Use métodos @ArchTest com JavaClasses quando o pacote raiz ou o modo de execução for dinâmico.
- Dê nomes expressivos às regras e separe obrigatórias de recomendadas.
- Mantenha cada regra focada em uma decisão verificável.
- Não use testes arquiteturais para substituir testes unitários, de integração ou de contrato.

## Entregáveis

Ao criar ou revisar regras, entregue:

1. a classe completa e compilável;
2. as dependências somente quando a implementação tiver sido solicitada e elas estiverem ausentes;
3. a classe Application usada como âncora;
4. archunit.properties e o store versionado, se houver freeze;
5. o comando de teste do módulo;
6. a lista de decisões, regras obrigatórias, regras recomendadas e violações preexistentes;
7. o plano de migração quando o projeto for brownfield.

Antes de finalizar, confirme a versão do ArchUnit, compile a suíte e revise falsos positivos. Consulte [examples.md](references/examples.md) para os padrões de implementação e [architectural-analysis.md](references/architectural-analysis.md) para análise e evolução.


