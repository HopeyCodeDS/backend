package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.ports.in.StorageFeeCalculationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyStorageFeeSchedulerImpl {
    
    private final StorageFeeCalculationUseCase storageFeeCalculationUseCase;
    
    // Runs every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void calculateDailyStorageFees() {
        log.info("Starting scheduled daily storage fee calculation at 9:00 AM");
        
        try {
            // Calculate fees for the PREVIOUS day (end of yesterday)
            LocalDate calculationDate = LocalDate.now().minusDays(1);
            storageFeeCalculationUseCase.calculateDailyStorageFees(calculationDate);
            log.info("Scheduled daily storage fee calculation completed successfully for {}", calculationDate);
        } catch (Exception e) {
            log.error("Error during scheduled daily storage fee calculation: {}", e.getMessage(), e);
        }
    }
} 