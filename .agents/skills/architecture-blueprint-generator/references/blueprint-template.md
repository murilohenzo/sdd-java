# Template do Project Architecture Blueprint

Use este modelo como estrutura final. Remova seções sem evidência e marque lacunas em vez de inventar conteúdo.

~~~markdown
# Blueprint arquitetural — [nome do projeto]

> Status: observado | em revisão
> Última análise: [AAAA-MM-DD]
> Escopo: [repositório, módulo ou fluxo]
> Confiança geral: alta | média | baixa

## 1. Resumo executivo

Explique em poucos parágrafos o que o sistema faz, qual é sua topologia e quais decisões arquiteturais são mais importantes.

## 2. Evidências e limites

### Fontes consultadas

| Fonte | Evidência utilizada |
|---|---|
| AGENTS.md | contexto operacional e módulos |
| pom.xml/build.gradle | tecnologias e versões |
| configurações | portas, perfis e integrações |
| código | responsabilidades e dependências |

### Convenções de leitura

Classifique afirmações como:

- Observado: comprovado diretamente no código ou configuração.
- Inferido: conclusão sustentada por mais de uma evidência.
- Não confirmado: informação que não foi encontrada.
- Recomendado: mudança proposta, não comportamento atual.

## 3. Stack e versões

| Área | Tecnologia | Versão | Evidência | Confiança |
|---|---|---|---|---|
| Linguagem | | | | |
| Framework | | | | |
| Persistência | | | | |
| Mensageria | | | | |
| Build | | | | |
| Runtime | | | | |

## 4. Mapa de módulos e serviços

Para cada módulo, informe:

- responsabilidade observada;
- tipo: microservice, gateway, biblioteca, provider ou infraestrutura;
- tecnologia e versão;
- entradas e saídas;
- porta da aplicação e do actuator, se confirmadas;
- dependências de runtime;
- configuração e variáveis externas;
- comandos de teste, build e execução;
- limitações ou comportamento incompleto.

## 5. Visão de contexto

Inclua diagrama Mermaid apenas com atores, módulos e sistemas externos comprovados.

## 6. Visão de containers e comunicação

Documente:

- chamadas HTTP síncronas;
- eventos e mensagens assíncronas;
- broker, exchanges, filas ou tópicos, quando encontrados;
- identidade e autorização;
- bancos e ownership;
- gateway e roteamento;
- dependências externas.

## 7. Arquitetura interna

Descreva a estrutura por módulo:

- domínio e regras de negócio;
- aplicação e casos de uso;
- adapters de entrada e saída;
- infraestrutura e composição;
- ports e implementações;
- direção das dependências;
- divergências entre a estrutura prescrita e a encontrada.

## 8. Dados

Mapeie somente o que for confirmado:

- bancos e schemas;
- entidades e agregados;
- repositories e queries;
- migrations;
- outbox, eventos ou sincronização;
- cache;
- ownership e consistência.

## 9. Preocupações transversais

Documente a implementação atual de:

- autenticação e autorização;
- validação;
- erros e contratos HTTP;
- transações;
- logging e observabilidade;
- resiliência;
- configuração e secrets;
- auditoria.

Use não confirmado para recursos não encontrados.

## 10. Arquitetura de testes

Relacione testes unitários, slices, integração, contratos e end-to-end. Informe ferramentas, dependências externas, cobertura conhecida e comandos reais.

## 11. Deployment e operação

Documente containers, compose, imagens, portas expostas, perfis, variáveis, ordem de inicialização e dependências de ambiente.

## 12. Padrões de extensão

Para cada nova funcionalidade:

1. ponto de entrada;
2. caso de uso ou componente central;
3. port necessário;
4. adapter ou integração;
5. configuração;
6. testes;
7. contrato e documentação a atualizar.

## 13. Divergências, riscos e dívida

Separe:

- divergência da documentação;
- violação arquitetural;
- risco operacional;
- dívida técnica;
- recomendação futura.

Priorize por impacto e esforço. Não apresente recomendação como fato atual.

## 14. Perguntas em aberto

Liste perguntas que impedem maior confiança e o arquivo ou evidência que deve ser consultado.

## 15. Governança do blueprint

Explique quando atualizar o documento: novo serviço, nova integração, mudança de banco, alteração de contrato, mudança de deployment ou mudança de direção de dependências.
~~~

