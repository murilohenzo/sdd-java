# Exemplos de Contract-First/OpenAPI

## Contrato mínimo com erros reutilizáveis

~~~yaml
openapi: 3.0.3
info:
  title: API de usuários
  version: 1.0.0
paths:
  /users/{id}:
    get:
      tags: [Usuários]
      operationId: buscarUsuarioPorId
      parameters:
        - $ref: '#/components/parameters/UserId'
      responses:
        '200':
          description: Usuário encontrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/UserResponse'
        '404':
          $ref: '#/components/responses/NotFound'
        default:
          $ref: '#/components/responses/UnexpectedError'
      security:
        - bearerAuth: []
components:
  parameters:
    UserId:
      name: id
      in: path
      required: true
      description: Identificador do usuário
      schema:
        type: string
        format: uuid
  schemas:
    UserResponse:
      type: object
      required: [id, name]
      properties:
        id:
          type: string
          format: uuid
        name:
          type: string
          minLength: 1
    ErrorResponse:
      type: object
      required: [code, message]
      properties:
        code:
          type: string
        message:
          type: string
  responses:
    NotFound:
      description: Recurso não encontrado
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/ErrorResponse'
    UnexpectedError:
      description: Erro inesperado
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/ErrorResponse'
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
~~~

O exemplo usa identificador de operação estável, parâmetro reutilizável, respostas nomeadas, `$ref`, validação declarativa e segurança explícita. Ajuste os nomes e o formato de erro às convenções do módulo.

## Configuração Maven ilustrativa

Este exemplo é deliberadamente genérico. Substitua a versão e as opções somente pelos valores confirmados no build do módulo:

~~~xml
<plugin>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-generator-maven-plugin</artifactId>
  <version>${openapi-generator.version}</version>
  <executions>
    <execution>
      <goals><goal>generate</goal></goals>
      <configuration>
        <inputSpec>${project.basedir}/src/main/resources/swagger/openapi.yaml</inputSpec>
        <generatorName>spring</generatorName>
        <library>spring-boot</library>
        <output>${project.build.directory}/generated-sources/openapi</output>
        <configOptions>
          <delegatePattern>true</delegatePattern>
          <useSpringBoot3>true</useSpringBoot3>
          <useJakartaEe>true</useJakartaEe>
          <useBeanValidation>true</useBeanValidation>
        </configOptions>
      </configuration>
    </execution>
  </executions>
</plugin>
~~~

Não copie apenas o trecho: `apiPackage`, `modelPackage`, mappings, sufixos e arquivos suportados podem ser necessários para preservar o contrato do módulo.

## Implementação manual com delegate

O controller e a interface abaixo são gerados. A lógica manual fica no delegate, que chama uma porta da aplicação e converte o resultado para a representação definida pelo contrato:

~~~java
@Component
public class UsersApiDelegateImpl implements UsersApiDelegate {
    private final FindUserByIdUseCase findUserById;
    private final UserRepresentationMapper mapper;

    public UsersApiDelegateImpl(
            FindUserByIdUseCase findUserById,
            UserRepresentationMapper mapper) {
        this.findUserById = findUserById;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<UserRepresentation> buscarUsuarioPorId(UUID id) {
        User user = findUserById.execute(id);
        return ResponseEntity.ok(mapper.toRepresentation(user));
    }
}
~~~

O nome real do método e o tipo de retorno devem ser obtidos da interface gerada. Não altere o arquivo gerado para fazer o exemplo compilar.

## Validação, geração e verificação

~~~powershell
cd <diretorio-do-modulo>
.\mvnw.cmd generate-sources
.\mvnw.cmd test
.\mvnw.cmd clean package
~~~

Se a CLI compatível estiver instalada, a validação isolada pode ser executada assim:

~~~powershell
openapi-generator-cli validate -i src/main/resources/swagger/openapi.yaml
~~~

No fluxo normal, use o plugin configurado no build. A CLI com versão diferente pode produzir código diferente e não deve substituir silenciosamente a geração oficial.

## Mudança compatível e incompatível

Compatível na maioria dos consumidores:

~~~yaml
properties:
  preferredName:
    type: string
    description: Nome de exibição opcional
~~~

Potencialmente incompatível:

~~~yaml
required: [id, name, preferredName]
~~~

Tornar `preferredName` obrigatório exige avaliar consumidores existentes, versão do contrato e estratégia de migração. Não basta regenerar os DTOs.
