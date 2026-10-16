---
name: criar-commit
description: Prepara mensagens Conventional Commits ou cria commits locais quando solicitado, inclusive ao encerrar uma entrega SDD. Não executa push nem se ativa apenas porque uma tarefa terminou.
---

# Criar commit

## Argumentos

São instruções ao agente, não uma CLI instalada. Todos são opcionais:

```text
criar-commit [--type <tipo>] [--scope <escopo>] [--summary "resumo"]
             [--breaking "impacto e migração"] [--prd <slug>] [--task <n>]
             [--files "caminho" ...] [--dry-run]
```

- `--type`, `--scope`, `--summary`: sobrescrevem a inferência do diff; valide sua coerência. Sem escopo claro, omita-o.
- `--breaking`: descreve quebra de compatibilidade; use `!` e footer `BREAKING CHANGE:`. Sinalize também quebras comprovadas sem esse argumento.
- `--prd`, `--task`: vínculo SDD opcional; tarefa aceita `1` ou `1.0` para `task_1.md`. Resolva o slug pelo argumento ou contexto inequívoco; não adivinhe.
- `--files`: caminhos literais relativos à raiz; aceita lista ou repetição. Seleciona o estado completo desses arquivos, inclusive alterações ainda não staged. Sem esse argumento, use somente o index existente.
- `--dry-run`: mostre mensagem e arquivos previstos sem alterar index ou histórico. Pedido apenas de mensagem tem o mesmo efeito; pedido para criar commit autoriza a execução local.

## Execução enxuta

1. Reuse contexto e validações da entrega. Confira `git status --short` e o diff selecionado; leia arquivos não rastreados selecionados. Consulte apenas regras de commit existentes e histórico recente se a convenção ainda for desconhecida. Não releia todas as specs/skills nem navegue na web por rotina.
2. Confirme uma mudança coesa, sem conflitos ou conteúdo sensível. Sem seleção ou com escopo ambíguo, peça apenas o dado faltante. Com `--files`, identifique arquivos já staged fora da seleção antes de qualquer alteração: não os inclua, remova ou restaure silenciosamente; esclareça o escopo. Não use `git add .`, `git add -A`, `commit -a` ou caminhos como comandos.
3. Redija a mensagem a partir da mudança real. Use convenções locais; na ausência, tipos minúsculos, resumo curto no idioma do projeto/usuário e sem ponto final. `feat` adiciona capacidade; `fix` corrige defeito; use `docs`, `refactor`, `test`, `build`, `ci`, `perf`, `style` ou `chore` conforme o efeito. Não invente escopo ou limite obrigatório de caracteres.
4. Havendo vínculo SDD, confira somente a tarefa/índice necessário; acrescente `Refs: tasks/prd-<slug>/task_<n>.md` (ou `prd.md` sem tarefa) apenas para caminho existente. Não marque tarefa concluída por criar commit.
5. Em simulação, entregue a proposta sem executar build/testes. Em execução autorizada, reuse checks válidos para o conteúdo selecionado; execute os exigidos que faltarem, sem inventar resultados nem ignorar falhas. Adicione caminhos selecionados com `git --literal-pathspecs add -- <caminhos>`; confira o diff completo do index e `git diff --cached --check`. Sem mudanças, não crie commit vazio.
6. Grave a mensagem em arquivo temporário fora da seleção e use `git commit -F <arquivo>`. Respeite hooks; se falharem ou alterarem arquivos, revise o estado antes de tentar novamente. Não faça bypass, amend, reset, push nem altere identidade/configuração Git como efeito colateral.
7. Confirme hash, mensagem e arquivos do commit criado e o estado restante. Entregue hash/título e limitações relevantes; remova somente o temporário criado nesta execução.

## Formato

`<tipo>[(escopo)][!]: <resumo>`; corpo opcional explica motivo/impacto, separado por linha vazia. Footers também ficam separados por linha vazia. Base: [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/).
