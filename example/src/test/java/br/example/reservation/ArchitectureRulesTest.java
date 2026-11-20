package br.example.reservation;

import br.example.reservation.core.domain.Reservation;
import br.example.reservation.core.domain.ReservationConfirmation;
import br.example.reservation.core.domain.ReservationRejected;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = ReservationApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureRulesTest {

    private static final String ROOT = ReservationApplication.class.getPackageName();
    private static final String DOMAIN = ROOT + ".core.domain..";
    private static final String APPLICATION = ROOT + ".core.application..";
    private static final String INBOUND_PORT = ROOT + ".core.application.port.inbound..";
    private static final String OUTBOUND_PORT = ROOT + ".core.application.port.outbound..";
    private static final String HTTP = ROOT + ".adapters.inbound.http..";
    private static final String MEMORY = ROOT + ".adapters.outbound.memory..";
    private static final String INFRASTRUCTURE = ROOT + ".infrastructure..";

    @ArchTest
    static final ArchRule domainUsesOnlyTheJdkAndDomainTypes = classes().that().resideInAPackage(DOMAIN)
            .should().onlyDependOnClassesThat().resideInAnyPackage("java.lang..", "java.util..", DOMAIN);

    @ArchTest
    static final ArchRule applicationStaysInsideTheCore = classes().that().resideInAPackage(APPLICATION)
            .should().onlyDependOnClassesThat().resideInAnyPackage("java.lang..", "java.util..", DOMAIN, APPLICATION);

    @ArchTest
    static final ArchRule portsDependOnDomainAndTheirOwnContracts = CompositeArchRule
            .of(classes().that().resideInAPackage(INBOUND_PORT)
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage("java.lang..", "java.util..", DOMAIN, INBOUND_PORT))
            .and(classes().that().resideInAPackage(OUTBOUND_PORT)
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage("java.lang..", "java.util..", DOMAIN, OUTBOUND_PORT));

    @ArchTest
    static final ArchRule httpUsesTheInboundPortAndItsResultTypes = classes().that().resideInAPackage(HTTP)
            .should().onlyDependOnClassesThat(
                    resideInAnyPackage("java.lang..", "java.util..", "org.springframework.http..", "org.springframework.web..",
                            "com.fasterxml.jackson..", HTTP, INBOUND_PORT)
                            .or(type(Reservation.class))
                            .or(type(ReservationConfirmation.class))
                            .or(type(ReservationRejected.class))
                            .or(type(ReservationRejected.Reason.class)));

    @ArchTest
    static final ArchRule memoryImplementsTheOutboundPortWithoutDependingOnHttp = classes().that()
            .resideInAPackage(MEMORY)
            .should().onlyDependOnClassesThat().resideInAnyPackage("java.lang..", "java.util..", DOMAIN, OUTBOUND_PORT, MEMORY);

    @ArchTest
    static final ArchRule productionClassesBelongToAnExplicitBoundary = classes().that()
            .resideInAPackage(ROOT + "..")
            .and().doNotHaveFullyQualifiedName(ReservationApplication.class.getName())
            .should().resideInAnyPackage(DOMAIN, APPLICATION, HTTP, MEMORY, INFRASTRUCTURE);
}
