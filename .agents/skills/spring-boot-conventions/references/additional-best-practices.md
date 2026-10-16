# 15 boas práticas adicionais para Spring Boot

Aplique estas práticas somente depois de verificar a versão do módulo e as dependências reais. Elas complementam [conventions.md](conventions.md); não substituem decisões arquiteturais, testes ou políticas de segurança do projeto.

## 1. Externalize configuração e segredos

Mantenha valores por ambiente fora do código e use variáveis de ambiente, arquivos externos ou um secret manager. Não versione senhas, tokens ou chaves.

~~~yaml
spring:
  datasource:
    url: ${DB_URL}
    password: ${DB_PASSWORD}
~~~

Fonte: https://docs.spring.io/spring-boot/reference/features/external-config.html

## 2. Use profiles para ambiente, não para regra de negócio

Use profiles para selecionar infraestrutura e configuração de ambiente. Não espalhe decisões de negócio condicionadas por profile.

~~~yaml
spring:
  config:
    activate:
      on-profile: local
~~~

Fonte: https://docs.spring.io/spring-boot/reference/features/profiles.html

## 3. Valide configuração na inicialização

Agrupe propriedades relacionadas em ConfigurationProperties e valide valores obrigatórios durante o startup, evitando descobrir configuração inválida em produção.

~~~java
@ConfigurationProperties("clients.user")
@Validated
public record UserClientProperties(
        @NotBlank String baseUrl,
        @Positive int timeoutMs) {
}
~~~

Fonte: https://docs.spring.io/spring-boot/reference/features/external-config.html

## 4. Use proxyBeanMethods = false quando não houver chamadas entre @Bean

Configurações que apenas registram beans independentes podem usar a configuração lite para reduzir proxy desnecessário. Continue recebendo dependências pelos parâmetros dos métodos @Bean.

~~~java
@Configuration(proxyBeanMethods = false)
class MessagingConfiguration {
}
~~~

Fonte: https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html

## 5. Exponha Actuator com o menor escopo necessário

Não exponha todos os endpoints de gerenciamento publicamente. Exponha apenas o que a operação precisa e proteja endpoints sensíveis com a segurança do módulo.

~~~yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
~~~

Fonte: https://docs.spring.io/spring-boot/reference/actuator/monitoring.html

## 6. Separe liveness de readiness

Use liveness para indicar se a aplicação pode continuar executando e readiness para indicar se pode receber tráfego. Não faça liveness falhar apenas porque um banco ou broker externo está temporariamente indisponível.

~~~yaml
management:
  endpoint:
    health:
      probes:
        enabled: true
~~~

Fonte: https://docs.spring.io/spring-boot/reference/actuator/endpoints.html

## 7. Configure encerramento gracioso

Permita que requisições em andamento terminem durante o desligamento e valide o comportamento no ambiente de execução.

~~~yaml
server:
  shutdown: graceful
spring:
  lifecycle:
    timeout-per-shutdown-phase: 20s
~~~

Fonte: https://docs.spring.io/spring-boot/reference/web/graceful-shutdown.html

## 8. Use observabilidade com baixa cardinalidade

Para métricas e traces, prefira Micrometer Observation e tags com poucos valores possíveis. Não use IDs de usuário, tokens ou URLs completas como tags de alta cardinalidade.

~~~java
Observation.createNotStarted("user.registration", observationRegistry)
        .lowCardinalityKeyValue("result", "success")
        .observe(this::register);
~~~

Fonte: https://docs.spring.io/spring-boot/reference/actuator/observability.html

## 9. Faça logs acionáveis e sem dados sensíveis

Inclua contexto operacional, como identificador de correlação e resultado, mas nunca senhas, tokens ou payloads sensíveis. Passe a exceção como último argumento para preservar a stack trace.

~~~java
log.error("Falha ao publicar evento userId={} correlationId={}",
        userId, correlationId, exception);
~~~

Fonte: https://docs.spring.io/spring-boot/reference/features/logging.html

## 10. Configure CORS antes da cadeia de segurança

CORS deve ser restrito às origens, métodos e headers necessários. Em aplicações com Spring Security, garanta que o preflight seja processado antes da autenticação.

~~~java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("https://app.exemplo.com"));
    configuration.setAllowedMethods(List.of("GET", "POST"));
    return request -> configuration;
}
~~~

Fonte: https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html

## 11. Não desabilite CSRF sem justificar o modelo de autenticação

CSRF é relevante para aplicações que usam cookies ou sessão. APIs stateless com token no header podem ter uma decisão diferente, mas ela deve ser explícita e testada.

~~~java
http.csrf(Customizer.withDefaults());
~~~

Fonte: https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html

## 12. Armazene senhas com PasswordEncoder

Nunca armazene senha em texto puro ou com hash reversível. Use PasswordEncoder e permita que a configuração escolha um algoritmo seguro compatível com a política do projeto.

~~~java
@Bean
PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
~~~

Fonte: https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html

## 13. Controle @Async e @Scheduled com executores explícitos

Defina executor e scheduler quando houver carga relevante, limite concorrência e trate falhas. Lembre que chamadas internas na mesma classe não atravessam o proxy de @Async.

~~~java
@Async("notificationExecutor")
public void sendNotification(Notification notification) {
    notificationSender.send(notification);
}
~~~

Métodos @Scheduled devem ser idempotentes, não receber argumentos e ter timezone/overlap avaliados.

Fonte: https://docs.spring.io/spring-framework/reference/integration/scheduling.html

## 14. Use cache apenas com política de validade

Cache exige chave estável, TTL, invalidação e comportamento definido para erro ou atualização. Não use @Cacheable para esconder uma query lenta sem medir consistência e memória.

~~~java
@Cacheable(cacheNames = "pets", key = "#petId")
public PetResponse findPet(UUID petId) {
    return petReader.findById(petId);
}
~~~

Fonte: https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html

## 15. Use eventos transacionais para efeitos posteriores

Quando uma ação externa deve ocorrer somente depois do commit, vincule o listener à fase correta ou use outbox quando a entrega precisar ser confiável. Não publique integração como concluída antes da transação confirmar.

~~~java
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
public void handleUserCreated(UserCreated event) {
    notificationSender.send(event);
}
~~~

Fonte: https://docs.spring.io/spring-framework/reference/data-access/transaction/event.html

## Checklist para a LLM

Antes de aplicar uma destas práticas:

- confirme a versão do Spring Boot, Spring Framework e Spring Security;
- confirme que a dependência usada no exemplo existe no módulo;
- confirme se o módulo é MVC ou WebFlux;
- não copie propriedades de Boot 4 para módulos Boot 3 sem verificar compatibilidade;
- classifique a prática como obrigatória, recomendada ou dependente do contexto;
- atualize testes quando a prática alterar validação, segurança, observabilidade ou ciclo de vida.

