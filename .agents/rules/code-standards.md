---
name: code-standards
description: Padrões gerais para código Java legível, seguro e de baixa complexidade, adaptáveis às convenções reais do projeto.
---

# Padrões de código Java

Use estas regras em código Java novo ou alterado. Verifique formatadores, analisadores estáticos e convenções locais antes de aplicar limites numéricos.

## Regras obrigatórias

- Use nomes que expressem intenção, domínio e responsabilidade. Evite `Manager`, `Helper`, `Utils`, `Data` e abreviações sem significado contextual.
- Mantenha segredos, tokens, senhas e chaves fora do código, testes, fixtures e arquivos versionados. Use variáveis de ambiente, configuração externa ou secret manager conforme o ambiente.
- Preserve a causa ao encapsular exceções e não ignore falhas silenciosamente.
- Extraia números e strings mágicos quando representarem um conceito, limite, estado ou regra reutilizável.
- Declare variáveis perto do primeiro uso e limite seu escopo.
- Não altere manualmente código gerado; modifique a fonte ou o ponto de extensão apropriado.
- Respeite formatadores e verificações automatizadas configuradas no build.

## Preferências de legibilidade

- Prefira cláusulas guarda e retornos antecipados a condicionais profundamente aninhadas.
- Evite mais de três níveis de decisão. Quando ocorrer, extraia políticas ou operações nomeadas.
- Prefira até três parâmetros quando os valores formarem um conceito único; nesse caso, use command, request, criteria ou value object.
- Mantenha métodos focados. Trinta linhas são um sinal de revisão, não uma proibição automática.
- Mantenha classes coesas. Cem linhas são um sinal para revisar responsabilidades, não um limite universal.
- Evite linhas em branco usadas para esconder múltiplas responsabilidades dentro de um método; extraia blocos com intenção própria.
- Prefira imutabilidade quando ela não conflitar com o framework ou modelo adotado.

Exemplo com cláusulas guarda:

```java
public ProcessingResult process(ProcessingCommand command) {
    if (!processingPolicy.isAllowed(command)) {
        return ProcessingResult.rejected();
    }
    return processor.process(command);
}
```

Exemplo de objeto de parâmetros:

```java
public record SearchCriteria(String species, String city, int page, int size) {
}
```

## Comentários e documentação

Prefira código autoexplicativo. Use comentário somente para registrar uma razão não evidente, restrição externa, decisão de compatibilidade ou expressão complexa. Não narre o que o código já diz.

Javadoc pode ser necessário em APIs públicas, bibliotecas, extensões ou quando o projeto o exigir. Documente contrato, efeito e restrições; não replique a implementação.

## Exceções justificadas

Código gerado, classes de configuração declarativa, entidades exigidas por frameworks, testes parametrizados e APIs públicas podem ultrapassar os sinais de tamanho. Nesses casos, priorize coesão, legibilidade, compatibilidade e ferramentas do projeto.

Ao revisar uma exceção, explique o risco concreto. Não refatore apenas para satisfazer uma contagem de linhas.

Para exemplos de cada situação, consulte [examples/code-standards.md](examples/code-standards.md) somente quando necessário.
