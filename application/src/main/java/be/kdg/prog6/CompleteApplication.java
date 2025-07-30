package be.kdg.prog6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {
    "be.kdg.prog6.landsideContext.adapters.in.web",
    "be.kdg.prog6.landsideContext.core",
    "be.kdg.prog6.landsideContext.adapters.out.db",
    "be.kdg.prog6.landsideContext.adapters.out",
    "be.kdg.prog6.landsideContext.adapters.config",
    "be.kdg.prog6.landsideContext.adapters.in.amqp",
    "be.kdg.prog6.warehousingContext.adapters.in.web",
    "be.kdg.prog6.warehousingContext.core",
    "be.kdg.prog6.warehousingContext.adapters.out.db",
    "be.kdg.prog6.warehousingContext.adapters.out",
    "be.kdg.prog6.warehousingContext.adapters.config",
    "be.kdg.prog6.warehousingContext.adapters.in.amqp",
    "be.kdg.prog6.common.*"
})

public class CompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompleteApplication.class, args);
    }
}
