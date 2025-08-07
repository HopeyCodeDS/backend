package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import java.time.LocalDate;
import java.util.List;

public interface StorageFeeRepositoryPort {
    void save(StorageFee storageFee);
    List<StorageFee> findByCustomerNumber(String customerNumber);
    List<StorageFee> findByCalculationDate(LocalDate calculationDate);
    List<StorageFee> findByWarehouseNumber(String warehouseNumber);
    List<StorageFee> findAll();
} 