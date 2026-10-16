# Convenções Spring Boot e Spring Framework

## Como aplicar esta referência

Use esta referência ao criar ou revisar código que usa Spring Boot, Spring Framework, Spring MVC/WebFlux, Spring Data, Spring Security, mensageria ou configuração de beans.

Antes de sugerir uma API:

1. leia o pom.xml ou build.gradle do módulo alterado;
2. identifique a versão do Spring Boot e, quando relevante, do Spring Framework;
3. confirme se a dependência necessária está presente;
4. prefira APIs disponíveis nessa versão;
5. se houver diferença entre módulos, siga a versão do módulo atual;
6. declare qualquer limitação ou suposição.

Descubra as versões de Java e Spring Boot no build efetivo. Verifique também se Lombok está presente, configurado e aceito antes de usá-lo; não o adicione apenas para reduzir boilerplate em um exemplo.

Não assuma uma geração específica do Spring Boot. `ProblemDetail`, records e outras APIs devem ser usados somente após confirmar compatibilidade com a versão do módulo.

### Situações que ativam esta referência

Aplique esta orientação ao criar ou revisar controllers, services, casos de uso, configurações, clientes HTTP, DTOs, handlers de erro, transações, integrações com persistência ou mensageria e código que use Spring MVC ou WebFlux. Consulte também a regra de testes quando uma alteração incluir validação, endpoint, persistência ou tratamento de erro.

## Metadados e referências oficiais

- Nome: spring-boot-conventions.
- Escopo: criação e revisão de código Spring Boot/Spring Framework.
- Aplicar especialmente em beans, configurações, services, casos de uso, controllers, DTOs, handlers de exceção, transações e integrações.
- Spring Boot Reference: https://docs.spring.io/spring-boot/reference/
- Configuração externa do Spring Boot: https://docs.spring.io/spring-boot/reference/features/external-config.html
- Spring Framework — dependências e injeção: https://docs.spring.io/spring-framework/reference/core/beans/dependencies.html
- Spring Framework — tratamento de exceções MVC: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-exceptionhandler.html
- Spring Framework — transações declarativas: https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html

## 1. Criação e registro de beans

### Decisão rápida

- Use @Service para serviços de aplicação, casos de uso e regras de orquestração.
- Use @Component para componentes técnicos, adaptadores, mappers ou implementações que não tenham semântica de serviço.
- Use @Configuration para declarar configuração de infraestrutura.
- Use @Bean para bibliotecas de terceiros, objetos sem anotação própria ou instâncias cuja criação precise ser controlada.
- Use @ConfigurationProperties para propriedades externas agrupadas e tipadas.
- Não mova serviços para dentro de classes @Configuration apenas para centralizar beans.

Serviço de aplicação:


> Exemplo 1: consulte [examples.md#exemplo-1](examples.md#exemplo-1).


Componente técnico ou adapter:


> Exemplo 2: consulte [examples.md#exemplo-2](examples.md#exemplo-2).


Configuração de infraestrutura e dependências controladas:


> Exemplo 3: consulte [examples.md#exemplo-3](examples.md#exemplo-3).


Passe dependências pelos parâmetros dos métodos @Bean. Não chame diretamente outro método @Bean, como userClient(httpClient(), properties()); isso mistura criação manual com resolução do container e pode falhar especialmente quando proxyBeanMethods = false.

Propriedades externas agrupadas e tipadas:


> Exemplo 4: consulte [examples.md#exemplo-4](examples.md#exemplo-4).


Registre-as conforme o padrão do módulo, por exemplo com @ConfigurationPropertiesScan na aplicação ou @EnableConfigurationProperties(UserClientProperties.class) em uma configuração. Não espalhe @Value para dezenas de propriedades relacionadas.

### Não centralize serviços na configuração

Evite:


> Exemplo 5: consulte [examples.md#exemplo-5](examples.md#exemplo-5).


Prefira:


> Exemplo 6: consulte [examples.md#exemplo-6](examples.md#exemplo-6).


Use @Bean para UserService somente quando houver uma razão concreta, como implementação de biblioteca externa, factory condicionada ou composição que não possa ser expressa por component scanning.

## 2. Injeção de dependências e Lombok

Prefira injeção por construtor. Ela torna dependências obrigatórias, facilita testes unitários e evita objetos parcialmente inicializados.

Com Lombok já configurado:


> Exemplo 7: consulte [examples.md#exemplo-7](examples.md#exemplo-7).


Não use @Autowired em campos:


> Exemplo 8: consulte [examples.md#exemplo-8](examples.md#exemplo-8).


Não adicione Lombok a um módulo sem verificar se ele já está declarado, configurado e aceito pelas convenções do projeto.

Quando houver mais de um bean do mesmo tipo, use @Qualifier explicitamente. Se o qualifier for importante para a resolução, um construtor explícito é a opção mais clara:


> Exemplo 9: consulte [examples.md#exemplo-9](examples.md#exemplo-9).


Use @Primary somente quando houver uma implementação padrão realmente definida. Não esconda uma escolha importante em prioridade global.

## 3. Prevenção de dependências circulares

Dependência circular entre beans é problema de desenho. Não use @Lazy, injeção em campos ou setters como solução padrão para ocultar o ciclo.

Ciclo a evitar: OrderService depende de PaymentService enquanto PaymentService depende de OrderService.

Alternativas preferíveis:

- extraia a responsabilidade compartilhada para um terceiro serviço;
- reorganize o fluxo para que apenas um serviço coordene a operação;
- inverta a dependência por uma port/interface interna;
- publique um evento quando a comunicação não precisar ser síncrona.

Exemplo com coordenação explícita:


> Exemplo 10: consulte [examples.md#exemplo-10](examples.md#exemplo-10).


OrderService e PaymentService não precisam conhecer um ao outro. O caso de uso coordena a sequência.

## 4. DTOs

Prefira `record` para DTOs imutáveis de request e response quando a versão Java, o framework e o padrão do projeto forem compatíveis. Preserve o padrão existente quando uma migração não fizer parte do escopo.

DTO de entrada com validação:


> Exemplo 11: consulte [examples.md#exemplo-11](examples.md#exemplo-11).


DTO de saída:


> Exemplo 12: consulte [examples.md#exemplo-12](examples.md#exemplo-12).


Controller usando validação:


> Exemplo 13: consulte [examples.md#exemplo-13](examples.md#exemplo-13).


Não use record para entidades JPA, objetos que precisam de mutabilidade controlada, objetos construídos em múltiplas etapas ou modelos cujo framework não suporte adequadamente records.

Não exponha entidades JPA diretamente como resposta HTTP. Mapeie entidade para DTO.

### Paginação e contrato HTTP

Prefira parâmetros explícitos na API em vez de expor diretamente o tipo Pageable do Spring Data. Converta os parâmetros em um objeto próprio antes de chamar o caso de uso, evitando que detalhes de persistência vazem para o core.


> Exemplo 14: consulte [examples.md#exemplo-14](examples.md#exemplo-14).


Não transforme essa preferência em proibição absoluta quando o contrato do módulo já usar Pageable de forma deliberada.

### ResponseEntity

Retorne o DTO diretamente quando o status for fixo. Use ResponseEntity quando for necessário variar status, headers, cookies ou localização do recurso.


> Exemplo 15: consulte [examples.md#exemplo-15](examples.md#exemplo-15).


Use ResponseEntity quando, por exemplo, uma busca puder retornar 200 ou 404 conforme a existência do recurso.

Não exponha entidades JPA nem tipos internos de persistência no contrato HTTP.

## 4.1 Clientes HTTP

Escolha o cliente pela arquitetura do módulo e pela dependência realmente declarada:

- RestClient: chamadas síncronas em aplicações Spring MVC, se disponível na versão do módulo.
- WebClient: chamadas reativas quando o fluxo consumidor também for reativo.
- HttpExchange: cliente declarativo quando a versão do Spring e as dependências suportarem a API.
- RestTemplate: não use em código novo quando houver alternativa compatível, mas não trate seu uso existente como erro sem plano de migração.


> Exemplo 16: consulte [examples.md#exemplo-16](examples.md#exemplo-16).


Antes de sugerir esse exemplo, confirme que o módulo possui a dependência e a versão necessárias. Não introduza um cliente reativo em um fluxo MVC apenas por preferência tecnológica.

## 4.2 Persistência e observabilidade

- Não use queries derivadas cujo nome fique ilegível; prefira @Query, Specification ou uma consulta nomeada quando a regra ficar complexa.
- Rejeite consultas de lista sem paginação quando o volume puder crescer.
- Prefira Instant para timestamps de auditoria quando isso for compatível com o modelo, o banco e as convenções existentes.
- Mantenha entidades JPA, repositories e tipos de banco fora do domínio.
- Use logs estruturados ou pares chave/valor somente quando o stack de logging do módulo suportar essa prática.
- Nunca registre senhas, tokens, client secrets ou conteúdo sensível.

Não use a quantidade de predicados de uma query como regra fixa. Avalie legibilidade, plano de execução, volume esperado e responsabilidade da consulta.

Exemplo de log útil sem segredo:


> Exemplo 17: consulte [examples.md#exemplo-17](examples.md#exemplo-17).


## 5. Exceções

Use checked exceptions somente quando o chamador puder tratar ou recuperar a situação e essa decisão fizer parte explícita do contrato.

Use unchecked exceptions como padrão para erros de domínio e aplicação:


> Exemplo 18: consulte [examples.md#exemplo-18](examples.md#exemplo-18).


Preserve a causa ao encapsular uma falha:


> Exemplo 19: consulte [examples.md#exemplo-19](examples.md#exemplo-19).


Não capture exceções genéricas para ignorá-las, retornar sucesso ou esconder falhas:


> Exemplo 20: consulte [examples.md#exemplo-20](examples.md#exemplo-20).


Classifique a falha antes de tratá-la:

- validação: entrada inválida; normalmente 400;
- negócio: operação não permitida pelo estado ou regra; frequentemente 409 ou 422;
- infraestrutura: dependência temporariamente indisponível; frequentemente 503;
- inesperada: falha não prevista; 500, com detalhe técnico apenas no log.

## 6. Tratamento de erros HTTP

Use `@RestControllerAdvice` e `@ExceptionHandler` para centralizar o mapeamento das exceções da API. Prefira `ProblemDetail` quando a versão efetiva do Spring Framework suportar a API.

Exemplo com mensagem segura:


> Exemplo 21: consulte [examples.md#exemplo-21](examples.md#exemplo-21).


O exemplo pressupõe um logger configurado na classe. Adapte a assinatura aos tipos e à versão reais do módulo.

A resposta deve ser consistente e segura, contendo quando disponíveis status HTTP correto, título estável, detalhe seguro, timestamp, path e identificador de correlação.

Nunca devolva ao cliente exception.getMessage() sem avaliar o conteúdo. Não exponha stack traces, SQL, nomes de tabelas, hosts, credenciais, tokens ou detalhes internos. Registre a exceção completa nos logs, controlando o acesso e evitando duplicar segredos.

Não crie handlers específicos para tecnologias não usadas. Por exemplo, só trate JDBCConnectionException se o módulo realmente usar Hibernate/JDBC e se houver uma política clara para essa falha. Escolha o status conforme a condição: 503 para indisponibilidade temporária conhecida e 500 para erro inesperado.

## 7. Transações

Use @Transactional na fronteira do caso de uso ou da operação de negócio que precisa ser atômica.


> Exemplo 22: consulte [examples.md#exemplo-22](examples.md#exemplo-22).


Use readOnly = true para consultas transacionais que realmente não alteram dados:


> Exemplo 23: consulte [examples.md#exemplo-23](examples.md#exemplo-23).


Considere propagation somente quando houver uma necessidade explícita, como uma operação interna que precisa participar da transação existente ou iniciar uma transação independente. Não adicione configurações de propagação por hábito.

No modo proxy padrão, chamadas internas entre métodos da mesma instância não passam pelo proxy e não ativam a interceptação transacional:


> Exemplo 24: consulte [examples.md#exemplo-24](examples.md#exemplo-24).


Para corrigir, coloque a fronteira transacional em um bean chamado externamente, extraia o método para outro serviço ou use uma abordagem programática quando realmente necessária.

Evite @Transactional indiscriminadamente em controllers ou em todos os métodos. O controller deve lidar com HTTP; a transação deve proteger a operação de negócio.

## Regras obrigatórias e preferências

Obrigatório:

- verificar a versão e as dependências do módulo antes de usar uma API;
- preferir injeção por construtor;
- não usar @Autowired em campos;
- não esconder ciclos com @Lazy, setters ou campos;
- não expor detalhes internos em respostas HTTP;
- preservar causas ao encapsular exceções;
- manter transações na fronteira apropriada do caso de uso.
- evitar nomes de tipos totalmente qualificados dentro de métodos e assinaturas; use imports e nomes simples.

Preferências:

- @Service para serviços e casos de uso;
- @Component para componentes técnicos e adapters;
- @Configuration/@Bean para infraestrutura e terceiros;
- @ConfigurationProperties para configuração agrupada;
- record para DTOs imutáveis;
- ProblemDetail quando compatível;
- @RequiredArgsConstructor quando Lombok já estiver configurado.
- cliente HTTP escolhido conforme o modelo MVC/WebFlux do módulo;
- parâmetros explícitos para paginação de APIs públicas;
- DTO direto como retorno quando não houver variação de status ou headers.

Exceções às preferências são aceitáveis quando documentadas pela versão, dependência, framework integrado ou necessidade concreta do módulo.

## Qualidade de código

Não escreva nomes totalmente qualificados dentro de métodos ou assinaturas. Prefira imports normais para manter navegação, formatação e leitura consistentes:


> Exemplo 25: consulte [examples.md#exemplo-25](examples.md#exemplo-25).



