# Exemplos de regras ArchUnit

Os exemplos abaixo são modelos. Antes de copiar, confirme a versão real do ArchUnit e adapte os pacotes ao módulo analisado.

## Âncora da análise

Use a classe principal da aplicação e exclua testes da importação:

~~~java
@AnalyzeClasses(
    packagesOf = Application.class,
    importOptions = ImportOption.DoNotIncludeTests.class
)
class ArchitectureRulesTest {
}
~~~

Quando a raiz for dinâmica, derive o pacote da classe âncora:

~~~java
@ArchTest
static void adapters_dependem_apenas_do_nucleo(JavaClasses classes) {
    String root = Application.class.getPackageName();

    ArchRule rule = noClasses()
        .that().resideInAPackage(root + ".adapters.inbound..")
        .should().dependOnClassesThat()
        .resideInAnyPackage(
            root + ".adapters.outbound..",
            root + ".infrastructure.."
        );

    rule.check(classes);
}
~~~

## Direção entre camadas

Use layeredArchitecture quando o módulo realmente possuir essas camadas. A assinatura pode variar entre versões; compile o exemplo contra a dependência declarada:

~~~java
@ArchTest
static final ArchRule camadas_respeitam_a_direcao =
    layeredArchitecture()
        .consideringOnlyDependenciesInLayers()
        .layer("Domain").definedBy("..core.domain..")
        .layer("Application").definedBy("..core.application..")
        .layer("Inbound").definedBy("..adapters.inbound..")
        .layer("Outbound").definedBy("..adapters.outbound..")
        .layer("Infrastructure").definedBy("..infrastructure..")
        .whereLayer("Domain").mayNotAccessAnyLayer()
        .whereLayer("Application").mayOnlyAccessLayers("Domain")
        .whereLayer("Inbound").mayOnlyAccessLayers("Application", "Domain")
        .whereLayer("Outbound").mayOnlyAccessLayers("Application", "Domain")
        .whereLayer("Infrastructure")
            .mayOnlyAccessLayers("Inbound", "Outbound", "Application", "Domain");
~~~

consideringOnlyDependenciesInLayers não substitui regras contra bibliotecas externas. Para isolar o domínio, escreva regras específicas.

## Isolamento de tecnologia no domínio

Quando o requisito for aplicável ao projeto, proíba dependências de frameworks diretamente:

~~~java
@ArchTest
static final ArchRule dominio_nao_depende_de_frameworks =
    noClasses()
        .that().resideInAnyPackage("..core.domain..")
        .should().dependOnClassesThat()
        .resideInAnyPackage(
            "org.springframework..",
            "jakarta.persistence..",
            "com.fasterxml.jackson..",
            "org.hibernate.."
        );
~~~

Para uma decisão sobre anotações, prefira verificar a anotação, em vez de inferir pelo nome do pacote:

~~~java
@ArchTest
static final ArchRule dominio_nao_deve_ter_anotacoes_spring =
    noClasses()
        .that().resideInAnyPackage("..core.domain..")
        .should().beAnnotatedWith(Component.class)
        .orShould().beAnnotatedWith(Service.class);
~~~

Inclua outras anotações somente se elas existirem no contrato e no classpath real.

## Ciclos entre features

Para detectar ciclos entre slices de uma aplicação:

~~~java
@ArchTest
static final ArchRule features_nao_possuem_ciclos =
    slices()
        .matching("..feature.(*)..")
        .should().beFreeOfCycles();
~~~

A convenção do padrão deve corresponder à árvore real. Se o projeto usa módulos explícitos e a versão do ArchUnit oferecer a API de módulos, prefira modelar esses módulos diretamente e registre a decisão.

## Regras de código com escopo arquitetural

Não transforme toda convenção em regra global sem avaliar o escopo:

~~~java
@ArchTest
static final ArchRule adapters_rest_nao_abrem_transacoes =
    noClasses()
        .that().resideInAnyPackage("..adapters.inbound.rest..")
        .should().beAnnotatedWith(Transactional.class);
~~~

Para métodos anotados, crie uma regra separada e confirme se o predicado de métodos escolhido existe na versão usada.

## Baseline de brownfield

Use freeze apenas para violações legadas explicitamente escolhidas:

~~~java
@ArchTest
static final ArchRule camadas_legadas =
    FreezingArchRule.freeze(camadas_respeitam_a_direcao);
~~~

Configuração inicial recomendada para evitar alterações silenciosas no CI:

~~~properties
freeze.store.default.path=archunit_store
freeze.store.default.allowStoreCreation=false
freeze.store.default.allowStoreUpdate=false
~~~

A criação ou atualização do store deve ocorrer somente em uma execução local controlada e o resultado deve ser revisado antes de ser versionado. Não coloque regras novas, de segurança ou de isolamento do domínio atrás do freeze.

## Regra customizada

Quando uma regra pronta não representar exatamente a decisão, crie uma regra específica e explique sua cobertura:

~~~java
@ArchTest
static void dominio_nao_acessa_clientes_externos(JavaClasses classes) {
    String root = Application.class.getPackageName();

    noClasses()
        .that().resideInAnyPackage(root + ".core.domain..")
        .should().dependOnClassesThat()
        .resideInAnyPackage(root + ".adapters.outbound..")
        .check(classes);
}
~~~

Regras customizadas devem continuar legíveis, ter um nome expressivo e falhar por uma razão arquitetural identificável.

