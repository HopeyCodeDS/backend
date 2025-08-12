package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StorageFeeRepositoryPort {
    void save(StorageFee storageFee);
    
    List<StorageFee> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<StorageFee> findByCalculationDate(LocalDate calculationDate);
    
    boolean existsByCalculationDate(LocalDate calculationDate);
    
    Optional<StorageFee> findTopByOrderByCalculationDateDesc();
    
    List<StorageFee> findAll();
    
    List<StorageFee> findBySellerId(String sellerId);
    
    List<StorageFee> findBySellerIdAndCalculationDateBetween(String sellerId, LocalDate startDate, LocalDate endDate);
    
    List<StorageFee> findBySellerIdAndCalculationDate(String sellerId, LocalDate calculationDate);
    
    boolean existsByCalculationDateAndWarehouseAndMaterial(LocalDate calculationDate, String warehouseNumber, String materialType);
} 