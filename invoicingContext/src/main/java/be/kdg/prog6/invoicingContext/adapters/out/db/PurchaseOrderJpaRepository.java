package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrderJpaEntity, UUID> {
    
    Optional<PurchaseOrderJpaEntity> findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrderJpaEntity> findByCustomerNumber(String customerNumber);
    List<PurchaseOrderJpaEntity> findByStatus(PurchaseOrder.PurchaseOrderStatus status);
    void deleteById(UUID purchaseOrderId);
} 