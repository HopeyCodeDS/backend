package be.kdg.prog6.invoicingContext.ports.in;

import java.time.LocalDate;

public interface StorageFeeCalculationUseCase {
    void calculateDailyStorageFees(LocalDate calculationDate);
} 