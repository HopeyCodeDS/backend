package be.kdg.prog6.invoicingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface StorageTrackingJpaRepository extends JpaRepository<StorageTrackingJpaEntity, UUID> {
    
    List<StorageTrackingJpaEntity> findByWarehouseNumberAndMaterialTypeOrderByDeliveryTimeAsc(
        String warehouseNumber, String materialType);
    
    List<StorageTrackingJpaEntity> findByCustomerNumber(String customerNumber);
    
    @Query("SELECT s FROM StorageTrackingJpaEntity s ORDER BY s.warehouseNumber, s.materialType, s.deliveryTime")
    List<StorageTrackingJpaEntity> findAllOrderedByWarehouseAndMaterial();

    boolean existsByPdtId(UUID pdtId);
} 