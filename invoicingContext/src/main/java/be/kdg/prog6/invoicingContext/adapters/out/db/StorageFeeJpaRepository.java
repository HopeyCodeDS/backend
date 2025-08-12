package be.kdg.prog6.invoicingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StorageFeeJpaRepository extends JpaRepository<StorageFeeJpaEntity, Long> {
    
    List<StorageFeeJpaEntity> findByCalculationDate(LocalDate calculationDate);
    List<StorageFeeJpaEntity> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByCalculationDate(LocalDate calculationDate);
    List<StorageFeeJpaEntity> findByCalculationDateAndWarehouseNumberAndMaterialType(
        LocalDate calculationDate, String warehouseNumber, String materialType);
    boolean existsByCalculationDateAndWarehouseNumberAndMaterialType(
        LocalDate calculationDate, String warehouseNumber, String materialType);
    
    List<StorageFeeJpaEntity> findTopByOrderByCalculationDateDesc();
    
    List<StorageFeeJpaEntity> findAll();
    
    List<StorageFeeJpaEntity> findBySellerId(String sellerId);
    
    List<StorageFeeJpaEntity> findBySellerIdAndCalculationDateBetween(
        String sellerId, LocalDate startDate, LocalDate endDate);
    
    List<StorageFeeJpaEntity> findBySellerIdAndCalculationDate(
        String sellerId, LocalDate calculationDate);
} 