# Perfil local — sdd-java

Última verificação: 2026-10-16. Escopo: arquivos presentes neste repositório.

## Fatos observados

- Este repositório contém um kit de instruções para desenvolvimento Java/Spring Boot orientado por especificações: `AGENTS.md`, `.agents/rules/`, `.agents/skills/` e templates.
- Não há `pom.xml`, `build.gradle`, `build.gradle.kts`, wrappers, código de aplicação, contratos de runtime ou infraestrutura de serviços nesta revisão.
- Portanto, Java, Spring Boot, Spring Framework, plugins, modelo web, portas e arquitetura de aplicação não se aplicam ao kit. Eles precisam ser descobertos no repositório que o adotar.
- A presença de uma skill ou do exemplo hexagonal não significa adoção dessa tecnologia ou arquitetura.

## Decisões locais

- `AGENTS.md` é a entrada e o roteador. Regras comuns do fluxo ficam em [spec-driven-development.md](../rules/spec-driven-development.md); operações ficam em [execution.md](../rules/execution.md).
- As skills SDD usam `tasks/prd-<slug>/` quando uma funcionalidade for especificada. Não crie uma funcionalidade fictícia para manter este kit.
- Referências e templates são carregados sob demanda. Metadados `name` e `description` identificam cada skill.
- Não há meta de cobertura Java nem adoção de blueprint, releases de contexto ou ADRs de aplicação.

## Validação do kit

Na raiz do repositório, com Python 3.10+:

```text
python scripts/validate_structure.py
python -m unittest discover -s scripts/tests -v
```

O validador usa somente a biblioteca padrão; verifica integridade estrutural e links locais, não a qualidade semântica de uma especificação. Para revisão semântica de funcionalidades, use a skill `validar-spec`.

## Histórico e reutilização

O perfil anterior descrevia Pet Adoption System sem os módulos correspondentes neste checkout. Seu conteúdo original foi preservado em [arquivo histórico](../../docs/archive/profile-pet-adoption.original.txt), fora do carregamento de contexto normal. Não o use como configuração deste kit.

Ao adotar o kit em uma aplicação, substitua este perfil com [profile.example.md](profile.example.md) preenchido a partir do novo repositório. Não copie fatos, comandos nem portas deste histórico.
