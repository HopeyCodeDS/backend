package be.kdg.prog6.invoicingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    
    List<StorageFeeJpaEntity> findBySellerId(UUID sellerId);
    
    List<StorageFeeJpaEntity> findBySellerIdAndCalculationDateBetween(
        UUID sellerId, LocalDate startDate, LocalDate endDate);
    
    List<StorageFeeJpaEntity> findBySellerIdAndCalculationDate(
        UUID sellerId, LocalDate calculationDate);
} 