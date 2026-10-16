---
name: architecture
description: Orienta a descoberta e preservação da arquitetura real antes de criar pacotes, módulos ou regras de dependência.
---

# Decisões de arquitetura e estrutura

Não existe uma estrutura universal para projetos Spring Boot. Antes de mover ou criar código, identifique a arquitetura adotada no perfil local, ADRs, pacotes, imports e testes arquiteturais.

## Descoberta

1. Localize o pacote raiz e os módulos de build.
2. Determine se a organização é por feature, camada, módulo, contexto de negócio ou combinação.
3. Mapeie a direção efetiva das dependências.
4. Identifique contratos públicos, ownership de dados e fronteiras de integração.
5. Compare documentação e implementação, registrando divergências.

## Perfis suportados

- Hexagonal/Ports and Adapters: aplique [architectures/hexagonal.md](architectures/hexagonal.md) somente quando selecionado pelo projeto.
- Arquitetura em camadas: preserve a direção definida pelo projeto e evite atalhos entre apresentação, aplicação, domínio e persistência.
- Package by feature/modular: mantenha cada feature coesa, com API publicada explícita e detalhes internos encapsulados.
- Outros estilos: siga ADRs e evidências locais; não force um dos perfis acima.

## Regras comuns

- Nomes de pacotes devem expressar responsabilidade ou domínio.
- Não crie `util`, `common` ou `shared` como destino automático; exija responsabilidade e consumidores claros.
- Não introduza interface, camada ou módulo sem uma fronteira ou variação real.
- Evite ciclos e dependências implícitas entre módulos.
- Tipos de transporte, persistência e framework não devem vazar para camadas que a arquitetura declara independentes.
- Código compartilhado deve ter ownership, estabilidade e motivo de compartilhamento definidos.
- Mudanças de arquitetura exigem atualização da documentação e, quando configurado, dos testes ArchUnit.

Use a skill [archunit-rules](../skills/archunit-rules/SKILL.md) somente quando a tarefa envolver validação arquitetural automatizada.
