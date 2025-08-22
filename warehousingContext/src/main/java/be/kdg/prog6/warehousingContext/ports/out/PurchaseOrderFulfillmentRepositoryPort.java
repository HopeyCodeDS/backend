package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderFulfillmentRepositoryPort {
    void save(PurchaseOrderFulfillmentTracking fulfillment);
    Optional<PurchaseOrderFulfillmentTracking> findById(UUID trackingId);
    Optional<PurchaseOrderFulfillmentTracking> findByPurchaseOrderNumber(String purchaseOrderNumber);
    List<PurchaseOrderFulfillmentTracking> findByStatus(PurchaseOrderFulfillmentTracking.FulfillmentStatus status);
    List<PurchaseOrderFulfillmentTracking> findByCustomerNumber(String customerNumber);
    List<PurchaseOrderFulfillmentTracking> findAll();
} 