# Template de release de contexto

Salve em `docs/context/<projeto_analisado>/contexto.<NN>.md`, em que `NN` é o próximo número inteiro disponível com dois dígitos.

```markdown
# Contexto do projeto — `<projeto_analisado>`

**Release de análise:** `contexto.<NN>`
**Data:** YYYY-MM-DD
**Base:** [`contexto.<NN-1>.md`](contexto.<NN-1>.md) ou `primeira análise`
**Escopo:** repositório, módulo ou fluxo analisado
**Confiança:** alta, média ou baixa

## Resumo executivo

Estado atual em poucas linhas, sem misturar recomendação com fato.

## Fontes e evidências

Liste caminhos consultados, comandos executados e limitações.

## Arquitetura e responsabilidades

Descreva módulos, fronteiras, ownership e dependências reais.

## Comunicação e contratos

Inclua a matriz de relações confirmadas e eventos/contratos relevantes.

## Problemas e contexto de melhoria

Para cada item: prioridade, evidência, impacto, risco, dependências e próximo passo.

## Plano incremental

Organize ações em ordem segura, com critério de conclusão e testes necessários.

## Validação

Registre comandos, resultado e limitações. Não declare sucesso sem saída verificável.

## Questões em aberto

Decisões que exigem confirmação ou dados ausentes.

## Alterações desta release

Explique o que mudou em relação à release anterior e mantenha releases antigas intactas.
```

## Regras de versionamento

- `contexto.01.md` é a primeira release do projeto.
- Cada nova análise relevante cria a próxima release; não reutilize o número.
- Uma alteração documental pequena pode atualizar a release corrente somente se o usuário pedir explicitamente para corrigir a release, caso contrário crie a próxima.
- A release deve declarar a data e a diferença em relação à anterior.
- Não use o nome `latest` como substituto de uma release numerada.

