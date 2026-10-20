# sdd-java

Kit de instruções para usar LLMs no desenvolvimento Java/Spring Boot orientado por especificações. Não contém uma aplicação Java nem exige uma versão, arquitetura ou ferramenta de build específica.

## Organização

- [AGENTS.md](AGENTS.md): entrada curta, fontes de verdade, limites e roteamento.
- `.agents/project/profile.md` (quando existir): fatos e decisões exclusivos do repositório; use [o modelo](.agents/project/profile.example.md) ao adotá-lo.
- [.agents/rules/](.agents/rules/): restrições comuns, carregadas conforme o trabalho.
- [.agents/skills/](.agents/skills/): procedimentos por intenção, com templates e referências sob demanda.
- [scripts/validate_structure.py](scripts/validate_structure.py): verificação estrutural do kit.

O carregamento progressivo mantém as descrições disponíveis para seleção e lê o corpo de cada skill somente quando pertinente, conforme a [documentação oficial de skills](https://learn.chatgpt.com/docs/build-skills). O `AGENTS.md` concentra o roteamento; o mecanismo de descoberta e escopo está na [documentação oficial de AGENTS.md](https://learn.chatgpt.com/docs/agent-configuration/agents-md). Os Markdown em `.agents/rules/` são uma convenção deste kit, explicitamente referenciada, não uma configuração automática do cliente.

## Como adotar em um projeto

1. Integre o conteúdo de `AGENTS.md` às instruções existentes, preservando decisões locais e instruções dos módulos. Copie as rules e skills relevantes sem sobrescrever customizações.
2. Recrie `.agents/project/profile.md` com [profile.example.md](.agents/project/profile.example.md), a partir de builds, código, configuração, contratos e CI do projeto de destino.
3. Registre versões efetivas, arquitetura, comandos, diretórios, pré-requisitos e checks exigidos. Não adote tecnologias apenas por constarem em exemplos.
4. Ajuste links para o README do destino. Se quiser a validação estrutural, copie também `scripts/validate_structure.py` e seus testes; adapte o comando de validação ao ambiente.
5. Use uma funcionalidade real e pequena para avaliar o fluxo. Contexto versionado, blueprint e ADRs continuam opcionais até serem adotados pela equipe.

## Fluxo de trabalho

`PRD → TechSpec → tarefas → checagem de consistência → implementação → review → QA → reconciliação com o PRD`

A [regra SDD](.agents/rules/spec-driven-development.md) define entradas, saídas, critérios de prontidão, rastreabilidade, estados e controle de mudanças. A validação de consistência ocorre antes da implementação e quando as specs mudam; [validar-spec](.agents/skills/validar-spec/SKILL.md) permite executá-la como uma etapa dedicada. Ao final de um ciclo completo, review e QA alimentam a reconciliação entre requisitos, tarefas e evidências; lacunas retornam à etapa afetada. Pedidos parciais executam apenas as etapas abrangidas pelo escopo.

A [regra project-analysis](.agents/rules/project-analysis.md) apoia essas etapas quando há contexto desconhecido, impacto entre módulos ou documentação afetada. Ela delimita a investigação e o destino dos achados: perfil para fatos compartilhados, specs para a funcionalidade, blueprint ou release de contexto somente quando necessários e solicitados/adotados. Não acrescenta uma auditoria obrigatória a cada tarefa.

Exemplos de pedidos, usando o nome da skill em linguagem natural:

- “Use criar-prd para definir a funcionalidade de reserva de estoque.”
- “Use criar-techspec para a funcionalidade em tasks/prd-reserva-estoque.”
- “Use criar-tasks para essa TechSpec.”
- “Use validar-spec para verificar se reserva-estoque está pronta para implementação.”
- “Use executar-task para implementar a tarefa 1.0 de reserva-estoque.”
- “Use executar-review para revisar a implementação de reserva-estoque.”
- “Use executar-qa para validar os critérios de reserva-estoque e corrigir os bugs encontrados.”

Esses exemplos são pedidos ao agente, não comandos de terminal. Um único pedido também pode abranger várias etapas; não há obrigação de aprovar novamente o que já foi autorizado. Pedidos só de planejamento param na entrega dos documentos. Uma correção pequena independente pode seguir um fluxo proporcional, sem produzir specs artificiais.

## Commit ao encerrar uma entrega

A skill [criar-commit](.agents/skills/criar-commit/SKILL.md) prepara mensagens ou cria commits locais seguindo [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/), quando solicitado. Ela usa um único arquivo de instruções e reaproveita o contexto e as evidências do SDD, sem carregar templates ou reler todas as especificações.

Exemplos de pedidos ao agente:

```text
Use criar-commit --dry-run
Use criar-commit --type fix --scope estoque --summary "evita reserva duplicada"
Use criar-commit --prd reserva-estoque --task 1 --files "src/main/java/Reserva.java" "src/test/java/ReservaTest.java"
Use criar-commit --type feat --scope api --breaking "remove campo legado; use identificador" --dry-run
```

Sem `--files`, o alvo é o conteúdo já staged. `--files` aceita lista ou repetição e seleciona o estado completo dos caminhos indicados; `--dry-run` não altera index nem histórico. Tipo e resumo podem ser inferidos do diff. Os argumentos são interpretados pela LLM, não por um programa instalado. Commit não é uma etapa automática de toda tarefa e não executa push.

## Artefatos de uma funcionalidade

```text
tasks/prd-<slug>/
  prd.md             requisitos, escopo e critérios CA-*
  techspec.md        desenho e verificações TU/TI/TC/E2E/MAN
  tasks.md           índice, dependências e mapa de cobertura
  task_1.md          entrega, subtarefas e evidências
  spec-review.md     quando a validação dedicada for executada
  codereview.md      quando a revisão for executada
  qa.md              quando o QA for executado
  evidences/         apenas quando houver evidências a salvar
```

A cadeia `RF/RNF → CA → verificação → tarefa → evidência` torna cada obrigação verificável. IDs são estáveis; contratos e detalhes técnicos têm uma fonte principal e são referenciados pelos demais artefatos.

O código comprova o estado atual; a especificação vigente descreve a mudança pretendida. Essa separação aplica o princípio de dirigir a implementação pela especificação, apresentado pelo [GitHub Spec Kit](https://github.com/github/spec-kit/blob/main/spec-driven.md), sem importar suas convenções de CLI, biblioteca ou arquitetura como obrigações deste kit.

## Critérios de qualidade

- Requisito com resultado observável, origem e ausência de ambiguidade material.
- Decisões técnicas compatíveis com o projeto e diferenciadas de fatos observados.
- Tarefas com dependências válidas, critérios de conclusão e verificações suficientes.
- Evidências reais: `NÃO EXECUTADO` e `BLOQUEADO` não significam `PASSOU`.
- Backend, mensageria, biblioteca e batch podem ter QA sem navegador.
- Mudanças de requisitos propagadas aos artefatos dependentes, sem apagar histórico válido.
- Concisão obtida por leitura seletiva e remoção de duplicação; requisitos e contratos preservam linguagem precisa.

`pronto` significa que o documento satisfaz os critérios da etapa, não uma aprovação humana. Conclusão de uma tarefa também não equivale ao aceite de toda a funcionalidade.

## Manutenção e validação

Com Python 3.10+ disponível, na raiz:

```text
python scripts/validate_structure.py
python -m unittest discover -s scripts/tests -v
```

O validador não instala dependências nem modifica arquivos. Confere campos obrigatórios de descoberta, nomes/diretórios das skills e destinos de links Markdown locais. Não é parser completo de YAML/Markdown, não verifica âncoras ou URLs externas e não resolve links de saída em `TEMPLATE*.md` no diretório da skill. Templates e consistência semântica exigem revisão própria.

Os [cenários de avaliação](docs/sdd-validation.md) ajudam a verificar decisões do agente em pedidos realistas. Não há medição de ganho de qualidade ou tokens por modelo neste kit; menor contexto de entrada é uma melhoria estrutural, não garantia de desempenho.
