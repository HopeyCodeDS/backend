package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrder.PurchaseOrderStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderRepositoryPort {
    void save(PurchaseOrder purchaseOrder);
    Optional<PurchaseOrder> findById(UUID purchaseOrderId);
    Optional<PurchaseOrder> findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrder> findByCustomerNumber(String customerNumber);
    List<PurchaseOrder> findAll();
    List<PurchaseOrder> findByStatus(PurchaseOrderStatus status);
    void deleteById(UUID purchaseOrderId);
} 