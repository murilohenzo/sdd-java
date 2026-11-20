# Exemplo SDD — API de reserva de estoque

Componente fictício greenfield com Java 21, Maven e Spring Boot MVC. O estoque começa com dez unidades de `DEMO-1`. A API confirma a reserva e devolve a mesma confirmação quando recebe a mesma chave, o mesmo SKU e a mesma quantidade. O estado pertence a um único processo, que escuta em loopback. Reiniciar restaura o estoque e descarta as chaves.

Use este exemplo para acompanhar a cadeia **requisito → verificação → tarefa → evidência**, ver o que a revisão independente encontrou antes do código e reproduzir o comportamento implementado.

## Arquitetura hexagonal

O domínio decide saldo e idempotência. O caso de uso coordena a reserva pelas portas `ReserveStock` e `ReservationStore`. O adaptador HTTP traduz o contrato externo, o adaptador em memória controla o acesso exclusivo ao estado e a configuração Spring fornece a carga inicial e monta os componentes. [ADR das fronteiras](docs/architecture/adr/0002-ports-and-adapters.md)

O build executa o ArchUnit para conferir dependências de domínio, aplicação, portas e adaptadores. A revisão de código complementa os checks ao avaliar as causas de mudança de cada classe. [Regras executáveis](src/test/java/br/example/reservation/ArchitectureRulesTest.java) · [Revisão de código](tasks/prd-reserva-estoque/codereview.md)

## Execute a API

Na raiz do repositório, com Java 21 e Maven no PATH:

```text
mvn -f example/pom.xml -B -ntp verify
java -jar example/target/stock-reservation-0.1.0-SNAPSHOT.jar
```

Em outro PowerShell, envie a mesma requisição duas vezes:

```powershell
$reservation = @{
    Uri = 'http://127.0.0.1:8080/reservations'
    Method = 'Post'
    Headers = @{ 'Idempotency-Key' = 'demo-order-1' }
    ContentType = 'application/json'
    Body = '{"sku":"DEMO-1","quantity":6}'
}
Invoke-WebRequest @reservation | Select-Object StatusCode, Content
Invoke-WebRequest @reservation | Select-Object StatusCode, Content
```

A primeira chamada retorna `201`. A segunda retorna `200` com o mesmo identificador e deixa quatro unidades disponíveis. Reutilizar a chave com outra quantidade retorna `409 KEY_REUSED`. Use `Ctrl+C` no terminal da aplicação para encerrar. [Contrato HTTP](tasks/prd-reserva-estoque/techspec.md#contrato)

## Percorra o SDD

1. **PRD e arquitetura.** Leia os requisitos e o aceite. Veja por que o piloto usa estado em memória e qual compromisso essa escolha impõe aos consumidores. [PRD](tasks/prd-reserva-estoque/prd.md) · [Blueprint e trade-offs](tasks/prd-reserva-estoque/blueprint.md) · [ADR 0001](docs/architecture/adr/0001-consistencia-em-memoria.md)
2. **Desenho e tarefas.** Localize o contrato, os IDs de verificação e a divisão entre a entrega HTTP e a verificação concorrente. [TechSpec](tasks/prd-reserva-estoque/techspec.md) · [Plano](tasks/prd-reserva-estoque/tasks.md)
3. **Elephant-Goldfish.** Acompanhe o veredito `BLOQUEADO` da revisão 1, as mudanças em cada critério ou verificação e a liberação da revisão 2. [Relatório de revisão](tasks/prd-reserva-estoque/spec-review.md)
4. **Implementação e aceite.** Siga as tarefas da API, de concorrência e de fronteiras arquiteturais até o código e os resultados. [Tarefa 1.0](tasks/prd-reserva-estoque/task_1.md) · [Tarefa 2.0](tasks/prd-reserva-estoque/task_2.md) · [Tarefa 3.0](tasks/prd-reserva-estoque/task_3.md) · [Review de código](tasks/prd-reserva-estoque/codereview.md) · [QA](tasks/prd-reserva-estoque/qa.md)

Como exemplo de rastreabilidade, o `CA-05` exige que duas reservas concorrentes preservem o saldo. A TechSpec define `TU-04` para chaves distintas e `TU-05` para a mesma chave. A tarefa 2.0 executa ambos e o QA comprova que restaram quatro unidades nos dois cenários. [PRD](tasks/prd-reserva-estoque/prd.md#critérios-de-aceitação) · [Testes do caso de uso](src/test/java/br/example/reservation/ReserveStockServiceTest.java) · [Aceite verificado](tasks/prd-reserva-estoque/qa.md#aceite-verificado)

## Avalie a revisão independente

Em uma sessão com delegação em contexto novo, faça este pedido ao agente:

> Use validar-spec em modo Elephant-Goldfish para avaliar a prontidão de implementação em `example/tasks/prd-reserva-estoque/`. Faça uma passagem em contexto novo, somente leitura, usando PRD, blueprint, TechSpec, tarefas e ADR pertinente. Consolide no relatório os achados confirmados e as evidências.

O revisor recebe os artefatos vigentes e as fontes necessárias. Relatórios anteriores e correções esperadas ficam com o autor durante a passagem. A versão publicada já incorpora as correções do piloto. Use o relatório histórico para entender por que saldo exato, efeitos das rejeições e reinício passaram a ter verificações mais fortes. [Achados e resolução](tasks/prd-reserva-estoque/spec-review.md#achados-e-resolução)

Para comparar os modos em um exercício controlado:

1. Use uma cópia temporária das mesmas specs para cada modo e mantenha o mesmo modelo, configuração, escopo e critério de prontidão. Se quiser introduzir uma lacuna conhecida, faça isso nas duas cópias e deixe o resultado esperado com quem avalia.
2. Em sessões novas, execute a validação local em uma cópia e a revisão Elephant-Goldfish na outra. Registre o que cada modo identificou e confirme os achados nas fontes.
3. Conte achados válidos, falsos positivos, correções após a implementação e tempo até o aceite. Quando a telemetria estiver disponível, some os tokens de autoria, revisão, consolidação e correções.

O piloto registra uma passagem independente real e seus achados. A comparação A/B permanece como procedimento de avaliação. [Roteiro do kit](../docs/sdd-validation.md#comparação-entre-versões-do-kit)
