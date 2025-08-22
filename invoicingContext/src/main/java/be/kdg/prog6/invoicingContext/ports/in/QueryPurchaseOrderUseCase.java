package be.kdg.prog6.invoicingContext.ports.in;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryPurchaseOrderUseCase {
    List<PurchaseOrder> getAllPurchaseOrders();
    Optional<PurchaseOrder> getPurchaseOrderById(UUID purchaseOrderId);
    List<PurchaseOrder> getOutstandingPurchaseOrders();
    List<PurchaseOrder> getFulfilledPurchaseOrders();
    List<PurchaseOrder> getCancelledPurchaseOrders();
    List<PurchaseOrder> getPurchaseOrdersByCustomer(String customerId);
    List<PurchaseOrder> getPurchaseOrdersByStatus(String status);
}