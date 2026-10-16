# Exemplos da convenção Spring Boot

Estes exemplos foram extraídos da redação original da rule. Leia este arquivo quando precisar de código de referência; a orientação normativa permanece em [conventions.md](conventions.md).

## Exemplo 1

~~~java
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public UserResponse execute(RegisterUserCommand command) {
        User user = userRepository.save(User.create(command));
        return UserResponse.from(user);
    }
}
~~~

## Exemplo 2

~~~java
@Component
@RequiredArgsConstructor
public class UserEventMapper {
    private final ObjectMapper objectMapper;

    public UserEvent toEvent(User user) {
        return objectMapper.convertValue(user, UserEvent.class);
    }
}
~~~

## Exemplo 3

~~~java
@Configuration(proxyBeanMethods = false)
class HttpClientConfiguration {
    @Bean
    HttpClient httpClient(HttpClientProperties properties) {
        return HttpClient.newBuilder()
                .connectTimeout(properties.connectTimeout())
                .build();
    }

    @Bean
    UserClient userClient(HttpClient httpClient, UserClientProperties properties) {
        return new UserClient(httpClient, properties.baseUrl());
    }
}
~~~

## Exemplo 4

~~~java
@ConfigurationProperties(prefix = "clients.user")
public record UserClientProperties(URI baseUrl, Duration connectTimeout) {
}
~~~

## Exemplo 5

~~~java
@Configuration
class ApplicationConfiguration {
    @Bean
    UserService userService(UserRepository repository) {
        return new UserService(repository);
    }
}
~~~

## Exemplo 6

~~~java
@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository repository;
}
~~~

## Exemplo 7

~~~java
@Service
@RequiredArgsConstructor
public class FindPetUseCase {
    private final PetRepository petRepository;
    private final PetMapper petMapper;
}
~~~

## Exemplo 8

~~~java
@Autowired
private PetRepository petRepository;
~~~

## Exemplo 9

~~~java
@Service
public class NotificationService {
    private final MessageSender sender;

    public NotificationService(
            @Qualifier("rabbitMessageSender") MessageSender sender) {
        this.sender = sender;
    }
}
~~~

## Exemplo 10

~~~java
@Service
@RequiredArgsConstructor
class CheckoutService {
    private final OrderService orderService;
    private final PaymentService paymentService;

    public void checkout(CheckoutCommand command) {
        Order order = orderService.load(command.orderId());
        paymentService.authorize(order.total(), command.payment());
        orderService.confirm(order);
    }
}
~~~

## Exemplo 11

~~~java
public record CreatePetRequest(
        @NotBlank String name,
        @NotNull PetSpecies species,
        @Positive int age
) {
}
~~~

## Exemplo 12

~~~java
public record PetResponse(
        UUID id,
        String name,
        PetSpecies species,
        int age
) {
}
~~~

## Exemplo 13

~~~java
@PostMapping
ResponseEntity<PetResponse> create(@Valid @RequestBody CreatePetRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(createPetUseCase.execute(request));
}
~~~

## Exemplo 14

~~~java
@GetMapping
UserPageResponse list(
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "20") @Positive @Max(100) int size) {
    return findAllUsers.execute(new PageQuery(page, size));
}
~~~

## Exemplo 15

~~~java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
UserResponse create(@Valid @RequestBody CreateUserRequest request) {
    return createUserUseCase.execute(request);
}
~~~

## Exemplo 16

~~~java
@HttpExchange(accept = "application/json")
public interface UserClient {
    @GetExchange("/users/{id}")
    UserResponse findById(@PathVariable UUID id);
}
~~~

## Exemplo 17

~~~java
log.info("Usuário criado com sucesso: userId={}, correlationId={}",
        userId, correlationId);
~~~

## Exemplo 18

~~~java
public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(UUID petId) {
        super("Pet não encontrado: " + petId);
    }
}
~~~

## Exemplo 19

~~~java
try {
    storageClient.put(file);
} catch (StorageException exception) {
    throw new PetPhotoStorageException(
            "Não foi possível armazenar a foto do pet", exception);
}
~~~

## Exemplo 20

~~~java
try {
    process();
} catch (Exception exception) {
    return success();
}
~~~

## Exemplo 21

~~~java
@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(PetNotFoundException.class)
    ResponseEntity<ProblemDetail> handlePetNotFound(
            PetNotFoundException exception,
            HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                "O pet solicitado não foi encontrado.");
        problem.setTitle("Pet não encontrado");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ProblemDetail> handleUnexpected(
            Exception exception,
            HttpServletRequest request) {
        log.error("Falha inesperada ao processar {}", request.getRequestURI(), exception);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível concluir a operação.");
        problem.setTitle("Erro interno");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());
        return ResponseEntity.internalServerError().body(problem);
    }
}
~~~

## Exemplo 22

~~~java
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public UserResponse execute(RegisterUserCommand command) {
        User user = userRepository.save(User.create(command));
        eventPublisher.publish(new UserCreated(user.id()));
        return UserResponse.from(user);
    }
}
~~~

## Exemplo 23

~~~java
@Transactional(readOnly = true)
public PetResponse findById(UUID id) {
    return PetResponse.from(petRepository.findById(id)
            .orElseThrow(() -> new PetNotFoundException(id)));
}
~~~

## Exemplo 24

~~~java
@Service
class ImportService {
    public void importAll() {
        saveBatch();
    }

    @Transactional
    public void saveBatch() {
        // A chamada interna acima não ativa o proxy transacional.
    }
}
~~~

## Exemplo 25

~~~java
import jakarta.servlet.http.HttpServletResponse;

ResponseEntity<UserResponse> update(HttpServletResponse response) {
    return ResponseEntity.ok(userResponse);
}
~~~

