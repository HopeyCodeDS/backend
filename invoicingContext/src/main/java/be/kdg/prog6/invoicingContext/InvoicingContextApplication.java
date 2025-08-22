package be.kdg.prog6.invoicingContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InvoicingContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoicingContextApplication.class, args);
    }

}
