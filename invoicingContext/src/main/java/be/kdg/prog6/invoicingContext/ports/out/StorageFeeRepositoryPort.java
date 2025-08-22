package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StorageFeeRepositoryPort {
    void save(StorageFee storageFee);
    
    List<StorageFee> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<StorageFee> findByCalculationDate(LocalDate calculationDate);
    
    boolean existsByCalculationDate(LocalDate calculationDate);
    
    Optional<StorageFee> findTopByOrderByCalculationDateDesc();
    
    List<StorageFee> findAll();
    
    List<StorageFee> findBySellerId(UUID sellerId);

    List<StorageFee> findBySellerIdAndCalculationDateBetween(UUID sellerId, LocalDate startDate, LocalDate endDate);

    List<StorageFee> findBySellerIdAndCalculationDate(UUID sellerId, LocalDate calculationDate);
    
    boolean existsByCalculationDateAndWarehouseAndMaterial(LocalDate calculationDate, String warehouseNumber, String materialType);
}