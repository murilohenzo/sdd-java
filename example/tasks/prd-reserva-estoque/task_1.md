# Tarefa 1.0 — API de reserva e replay

- Estado — concluida.
- Dependências — nenhuma.
- Base — [PRD revisão 2](prd.md) e [TechSpec revisão 2](techspec.md).
- Critérios — CA-01, CA-02, CA-03, CA-04 e CA-06.

## Entrega e conclusão

- [x] Criar o módulo Maven e a borda HTTP com o contrato descrito.
- [x] Implementar saldo e confirmações como uma operação atômica no serviço.
- [x] Executar TC-01 a TC-04 e TU-01/TU-02 conforme a TechSpec.
- [x] Executar MAN-01 com o JAR empacotado.

## Execução

TC-01 a TC-04 e TU-01/TU-02 passaram em `mvn -B -ntp verify` com diretório `example/`. MAN-01 passou em duas inicializações sucessivas do JAR. Consulte os [resultados HTTP](qa.md#aceite-verificado) e o [procedimento de reinício](qa.md#man-01--escuta-local-e-reinício).
