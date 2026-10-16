# Perfil local — [nome do projeto]

Última verificação: [AAAA-MM-DD]. Escopo: [repositório/módulos]. Preencha somente fatos confirmados e decisões registradas; remova seções sem aplicação. Cite o arquivo que comprova cada configuração relevante.

## Identidade e evidências

- Responsabilidade: [descrição].
- Tipo: [aplicação, biblioteca, monorepo, kit documental ou outro].
- Fontes: [builds, configuração, código, CI e documentação consultados].
- Lacunas: [não encontrado, não confirmado ou não aplicável, com motivo].

## Plataforma e módulos

Para cada módulo: responsabilidade, ownership, caminho do build/wrapper, versões efetivas de Java, Spring Boot, Spring Framework e plugins relevantes, modelo de execução (MVC, WebFlux, Batch, nenhum ou outro), dependências e fronteiras.

Separe versões observadas das propostas para um projeto novo. Registre parent/BOM ou mecanismo que resolve versões herdadas.

## Arquitetura e contratos

- Organização observada e decisões adotadas, com links para regras/ADRs aplicáveis.
- APIs, eventos, migrations, código gerado, fontes e comando de geração.
- Integrações, persistência, autenticação, timeout e idempotência comprovados.
- Não selecione o perfil hexagonal apenas por ele estar disponível no kit.

## Execução e validação

Para cada comando, registre diretório, wrapper, perfis e pré-requisitos:

- Testes e checks exigidos:
- Build:
- Execução local, portas por perfil e configuração externa (nomes, nunca valores secretos):
- Infraestrutura necessária, isolamento e como encerrar somente recursos próprios:
- Limitações conhecidas:

## Convenções SDD

- Local dos artefatos: [padrão `tasks/prd-<slug>/` ou convenção existente].
- Etapas/checks adicionais exigidos pela equipe, se houver:
- Decisões que precisam de aprovação específica, se houver, e onde essa exigência está definida:
- Contratos, relatórios e evidências versionadas:
- Blueprint/contextos/ADRs adotados: [links ou não adotados].

## Outras decisões locais

- Formatador, analisadores, meta de cobertura se configurada.
- Bibliotecas aceitas e justificativas de exceção às regras genéricas.
- Estado atual versus mudanças planejadas e links para especificações vigentes.
