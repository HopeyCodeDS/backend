package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;

public interface PurchaseOrderSubmittedPort {
    void publishPurchaseOrderSubmitted(PurchaseOrder purchaseOrder);
} 