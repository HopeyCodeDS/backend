package be.kdg.prog6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "be.kdg.prog6.landsideContext.*",
    "be.kdg.prog6.warehousingContext.*", 
    "be.kdg.prog6.invoicingContext.*",
    "be.kdg.prog6.watersideContext.*"
})

public class CompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompleteApplication.class, args);
    }
}
