package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking.FulfillmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseOrderFulfillmentTrackingJpaRepository extends JpaRepository<PurchaseOrderFulfillmentTrackingJpaEntity, UUID> {
    
    Optional<PurchaseOrderFulfillmentTrackingJpaEntity> findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrderFulfillmentTrackingJpaEntity> findByStatus(FulfillmentStatus status);
    List<PurchaseOrderFulfillmentTrackingJpaEntity> findByCustomerNumber(String customerNumber);
} 