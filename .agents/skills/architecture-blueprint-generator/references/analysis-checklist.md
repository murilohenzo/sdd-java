# Checklist de análise arquitetural

## Descoberta

- [ ] O escopo da análise está explícito.
- [ ] AGENTS.md, regras e skills locais foram consultados.
- [ ] Todos os POMs, Gradle files ou manifests relevantes foram encontrados.
- [ ] A versão efetiva das tecnologias foi verificada no build.
- [ ] Classes de inicialização e pacotes raiz foram localizados.
- [ ] Arquivos de configuração, profiles, compose, Dockerfiles e CI foram inspecionados.
- [ ] Contratos OpenAPI, schemas, eventos e migrations foram procurados.
- [ ] Nenhuma informação sensível foi copiada.

## Mapa arquitetural

- [ ] Cada módulo tem responsabilidade baseada no código.
- [ ] Bibliotecas compartilhadas não foram confundidas com microservices.
- [ ] Portas e endpoints foram confirmados na configuração.
- [ ] Relações HTTP e eventos têm evidência.
- [ ] Bancos e ownership foram separados por serviço quando aplicável.
- [ ] A direção das dependências foi comparada ao contrato arquitetural.
- [ ] Padrões foram identificados por comportamento, não apenas por nomes de pastas.
- [ ] Divergências entre documentação e código estão listadas.

## Evidência e precisão

- [ ] Fatos, inferências e recomendações estão separados.
- [ ] Lacunas aparecem como não confirmado.
- [ ] Diagramas não possuem relações hipotéticas.
- [ ] Versões, comandos e caminhos foram revisados.
- [ ] O blueprint não contém segredos, tokens ou credenciais.
- [ ] Código incluído é curto, anonimizado e necessário para explicar um padrão.

## Validação e manutenção

- [ ] O blueprint aponta para fontes locais relevantes.
- [ ] Testes e comandos foram conferidos no módulo correto.
- [ ] Riscos e dívida estão priorizados.
- [ ] Existe uma seção de perguntas em aberto.
- [ ] Foram definidos eventos que exigem atualização do blueprint.
- [ ] O arquivo foi revisado para não contradizer AGENTS.md, a regra de arquitetura, o perfil local ou os builds.

