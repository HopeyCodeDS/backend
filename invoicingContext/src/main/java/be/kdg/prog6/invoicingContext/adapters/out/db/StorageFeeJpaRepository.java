package be.kdg.prog6.invoicingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface StorageFeeJpaRepository extends JpaRepository<StorageFeeJpaEntity, UUID> {
    
    List<StorageFeeJpaEntity> findByCustomerNumber(String customerNumber);
    List<StorageFeeJpaEntity> findByCalculationDate(LocalDate calculationDate);
    List<StorageFeeJpaEntity> findByWarehouseNumber(String warehouseNumber);
} 