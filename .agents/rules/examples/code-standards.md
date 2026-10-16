# Exemplos dos padrões de código Java

Consulte somente quando uma regra precisar de exemplo. Os limites são sinais de revisão e podem ter exceção justificada pelo framework ou pelo contrato local.

## Comentário desnecessário

Evite narrar a implementação:

```java
// Incrementa o número de tentativas
attempts++;
```

Prefira intenção explícita:

```java
retryState.registerFailure();
```

Um comentário é aceitável para explicar uma restrição externa ou expressão regular que não possa ser simplificada.

## Classe com responsabilidades demais

Evite uma classe que valide entrada, persista dados, publique eventos e formate resposta. Separe por responsabilidade quando a coesão diminuir:

```text
RegistrationValidator
RegistrationRepositoryAdapter
RegistrationEventPublisher
RegistrationResponseMapper
```

## Método extenso

Extraia operações que revelem intenção:

```java
public Registration register(RegistrationCommand command) {
    validate(command);
    Registration registration = createRegistration(command);
    Registration saved = repository.save(registration);
    eventPublisher.publishRegistered(saved);
    return saved;
}
```

## Condicionais aninhadas

Evite:

```java
if (account != null) {
    if (account.isActive()) {
        if (account.canOperate()) {
            processor.process(account);
        }
    }
}
```

Prefira cláusulas guarda:

```java
if (account == null || !account.isActive() || !account.canOperate()) {
    return;
}
processor.process(account);
```

## Muitos parâmetros

Evite:

```java
search(type, status, city, page, size);
```

Agrupe valores que formam um conceito:

```java
search(new SearchCriteria(type, status, city, page, size));
```

## Separação artificial dentro de método

Evite usar linhas em branco para disfarçar dois blocos independentes. Extraia a segunda responsabilidade:

```java
public void execute(Command command) {
    Result result = process(command);
    publish(result);
}
```

## Número ou string mágica

Evite:

```java
if (attempts >= 5) {
    throw new RetryLimitExceededException();
}
```

Prefira:

```java
private static final int MAX_RETRY_ATTEMPTS = 5;
```

## Variável distante do uso

Evite declarar estado temporário no início do método. Declare quando o valor estiver disponível e for necessário:

```java
Account account = accountRepository.getRequired(accountId);
return mapper.toResponse(account);
```

## Segredo no código

Evite:

```java
private static final String API_KEY = "valor-real";
```

Prefira configuração externa e tipada:

```java
@ConfigurationProperties("clients.partner")
public record PartnerClientProperties(String apiKey) {
}
```

O valor deve vir do mecanismo seguro adotado pelo ambiente, não de arquivo versionado.

## Nome genérico

Evite:

```java
class Manager {
    void process(Data data) {
    }
}
```

Prefira:

```java
class RegisterAccountUseCase {
    RegistrationResult register(RegisterAccountCommand command) {
        return registrationService.register(command);
    }
}
```
