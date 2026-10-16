# Boas práticas de análise arquitetural

## Princípio central

ArchUnit transforma uma decisão arquitetural em uma expectativa verificável. A análise deve começar pelo sistema real e pelo motivo da decisão, não por uma coleção de APIs ou por um modelo idealizado. A arquitetura documentada, os ADRs, os POMs e o código devem formar uma fonte de verdade coerente.

## Processo recomendado

### 1. Delimite o problema

Registre módulo, pacote raiz, objetivo da regra, classes incluídas e exclusões. Uma regra que não define escopo tende a produzir falsos positivos ou a deixar módulos importantes sem cobertura.

### 2. Faça inventário antes de escrever a regra

Mapeie pacotes, dependências, ciclos, annotations, classes de composição e pontos de entrada. Em brownfield, conte e categorize as violações existentes antes de escolher entre correção imediata, migração ou baseline.

### 3. Modele o nível correto

- Use camadas para direção entre responsabilidades.
- Use pacotes para fronteiras internas e acesso a subpacotes.
- Use slices ou módulos para detectar ciclos entre features.
- Use regras específicas para bibliotecas, annotations, nomes e pontos de entrada.
- Não use uma regra de camadas para responder a uma decisão que ela não modela.

### 4. Prefira dependências permitidas e proibidas explícitas

Uma regra deve deixar claro o que pode depender de quê. Combine regras positivas, que descrevem o contrato, com regras negativas, que protegem fronteiras críticas. Não confunda uma camada declarada com todo o classpath.

### 5. Preserve a direção para dentro

Em arquitetura hexagonal, o domínio não conhece adapters ou infraestrutura. Aplicação coordena casos de uso; adapters traduzem entradas e saídas; infraestrutura compõe configurações. Se uma exceção for necessária, registre o motivo e limite o acesso ao menor pacote possível.

### 6. Detecte ciclos cedo

Ciclos entre features, módulos ou camadas tornam a evolução mais cara. Execute verificações acíclicas em cada mudança relevante e trate um ciclo novo como falha arquitetural, salvo decisão registrada.

### 7. Use importação determinística

Ancore a análise em uma classe estável, exclua testes e confirme o pacote raiz. Evite importar um escopo maior que o módulo para não misturar arquiteturas independentes.

### 8. Mantenha regras pequenas

Uma regra deve responder a uma decisão. Separe isolamento do domínio, direção de camadas, ciclos e convenções. Regras pequenas oferecem diagnóstico melhor, revisão mais simples e migração controlada.

### 9. Nomeie a intenção

Nomes de regras e mensagens devem dizer qual decisão foi quebrada e qual parte do sistema precisa ser revisada. Evite nomes genéricos como architectureIsValid.

### 10. Evite falsos positivos

Confirme os tipos no classpath e os pacotes reais antes de proibir Spring, JPA, Lombok, brokers ou clientes. Não falhe por pacote opcional vazio e não use allowEmptyShould(true) para encobrir uma estrutura obrigatória ausente.

### 11. Valide a própria cobertura

Inclua classes representativas no módulo e revise se a regra realmente falha quando a dependência proibida é introduzida. Quando a regra for complexa, use fixtures ou um teste de exemplo separado para impedir que ela passe a não detectar nada.

### 12. Integre ao fluxo de qualidade

Execute regras arquiteturais junto com os testes do módulo no CI. A falha deve ser reproduzível localmente e a versão da biblioteca deve ser controlada pelo build. Não dependa de uma API que não foi verificada na versão declarada.

### 13. Registre decisões e exceções

Para cada exceção arquitetural, registre motivo, escopo, responsável, duração e plano de remoção. A suíte não deve ser a única documentação de uma decisão controversa.

### 14. Use fitness functions com limites úteis

Escolha poucas métricas acionáveis, como ausência de ciclos, dependências proibidas e tamanho do acoplamento entre módulos. Uma métrica sem limiar ou ação definida gera ruído, não governança.

### 15. Trate segurança e operação como contexto

Regras contra acesso a credenciais, endpoints ou frameworks só devem ser aplicadas quando o requisito e a tecnologia existirem no módulo. ArchUnit complementa, mas não substitui, análise de segurança, testes de integração ou verificação de configuração.

## Estratégia greenfield

- Defina a estrutura e a direção das dependências antes do primeiro crescimento do código.
- Faça regras obrigatórias falharem imediatamente.
- Crie apenas regras que representem decisões reais.
- Evite baseline: não há dívida histórica a preservar.
- Execute as regras no mesmo ciclo de revisão do código.

## Estratégia brownfield

- Capture um retrato inicial: pacotes, ciclos, dependências e violações.
- Separe regressões novas de violações anteriores.
- Corrija primeiro fronteiras de alto risco e dependências que ampliam o acoplamento.
- Use FreezingArchRule somente como mecanismo de ratchet para regras legadas escolhidas.
- Versione o store e bloqueie criação ou atualização automática no CI.
- Reduza o baseline a cada correção e defina um plano de remoção do freeze.
- Não congele regras novas, isolamento de segurança ou regras que deveriam começar a proteger imediatamente.

## Relatório mínimo de análise

Ao concluir uma análise, registre:

- contexto: greenfield, brownfield ou transição;
- módulo e pacote raiz;
- arquitetura encontrada e divergências do contrato;
- regras propostas, classificadas como obrigatórias ou recomendadas;
- violações existentes e estratégia de tratamento;
- exceções documentadas;
- comando de validação e resultado;
- versão do ArchUnit e APIs confirmadas;
- próximos passos de migração.

## Referências oficiais

- [ArchUnit User Guide](https://www.archunit.org/userguide/html/000_Index.html)
- [ArchUnit Library API](https://github.com/TNG/ArchUnit/blob/main/docs/userguide/008_The_Library_API.adoc)
- [ArchUnit Architectures API](https://github.com/TNG/ArchUnit/blob/main/archunit/src/main/java/com/tngtech/archunit/library/Architectures.java)
- [ArchUnit Mission](https://www.archunit.net/mission/)

