package be.kdg.prog6.warehousingContext.architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "be.kdg.prog6", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    // Layer definitions for the entire project
    private static final String DOMAIN_LAYER = "..domain..";
    private static final String ADAPTERS_IN_LAYER = "..adapters.in..";
    private static final String ADAPTERS_OUT_LAYER = "..adapters.out..";
    private static final String ADAPTERS_CONFIG_LAYER = "..adapters.config..";
    private static final String CORE_LAYER = "..core..";
    private static final String PORTS_IN_LAYER = "..ports.in..";
    private static final String PORTS_OUT_LAYER = "..ports.out..";

    @ArchTest
    static final ArchRule domainShouldNotDependOnAnyOtherLayerRule =
            noClasses().that().resideInAPackage(DOMAIN_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            ADAPTERS_IN_LAYER,
                            ADAPTERS_OUT_LAYER,
                            ADAPTERS_CONFIG_LAYER,
                            CORE_LAYER,
                            PORTS_IN_LAYER,
                            PORTS_OUT_LAYER
                    )
                    .because("Domain layer should be independent and not depend on other layers in hexagonal architecture.");

    @ArchTest
    static final ArchRule domainShouldNotDependOnInfrastructureRule =
            noClasses().that().resideInAPackage(DOMAIN_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..config..",
                            "..db..",
                            "..amqp..",
                            "..web..",
                            "..dto..",
                            "..mapper.."
                    )
                    .because("Domain should not depend on infrastructure concerns like databases, AMQP, web layers, DTOs, or mappers.");

    @ArchTest
    static final ArchRule adaptersShouldNotDependOnOtherAdaptersRule =
            noClasses().that().resideInAPackage(ADAPTERS_IN_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(ADAPTERS_OUT_LAYER, ADAPTERS_CONFIG_LAYER)
                    .because("Input adapters should not depend on output adapters or config.");

    @ArchTest
    static final ArchRule coreShouldNotDependOnAdaptersRule =
            noClasses().that().resideInAPackage(CORE_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(ADAPTERS_IN_LAYER, ADAPTERS_OUT_LAYER, ADAPTERS_CONFIG_LAYER)
                    .because("Core layer should not depend on adapters.");

    @ArchTest
    static final ArchRule adaptersShouldNotDependOnCoreRule =
            noClasses().that().resideInAPackage(ADAPTERS_IN_LAYER)
                    .should().dependOnClassesThat().resideInAnyPackage(CORE_LAYER)
                    .because("Adapters should depend on ports, not core implementation.");


    @Test
    void givenApplicationClasses_thenNoLayerViolationsShouldExist() {

        JavaClasses jc = new ClassFileImporter().importPackages("be.kdg.prog6.warehousingContext");

        Architectures.LayeredArchitecture arch = layeredArchitecture().consideringOnlyDependenciesInLayers()
                .layer("DRIVING_ADAPTERS").definedBy("..adapters.in..")
                .layer("DRIVING_PORTS").definedBy("..ports.in..")
                .layer("CORE").definedBy("..core..")
                .whereLayer("DRIVING_ADAPTERS").mayNotBeAccessedByAnyLayer()
                .whereLayer("DRIVING_PORTS").mayOnlyBeAccessedByLayers("DRIVING_ADAPTERS", "CORE");


        arch.check(jc);
    }

}
