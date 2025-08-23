package be.kdg.prog6.landsideContext.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "be.kdg.prog6.landsideContext")
public class CodeStyleTest {

    @ArchTest
    static final ArchRule weShouldUseListOfRule =
            noClasses().should().callMethod(Arrays.class, "asList", Object[].class)
                    .orShould().callMethod(Collections.class, "singletonList", Object.class)
                    .because("List.of() is the better function for creating lists (cfr immutability).");


    @ArchTest
    static final ArchRule weShouldNotUseSystemOutPrintln =
            noClasses().should().callMethod(System.class, "out")
                    .because("Use proper logging framework instead of System.out.println().");


    @ArchTest
    static final ArchRule weShouldUseLocalDateTimeInsteadOfDate =
            noClasses().should().callMethod(java.util.Date.class, "now")
                    .because("Use LocalDateTime instead of Date for better date/time handling.");

    @ArchTest
    static final ArchRule weShouldUseStringBuilderForStringConcatenation =
            noClasses().should().callMethod(String.class, "concat", String.class)
                    .because("Use StringBuilder for multiple string concatenations in loops.");
}