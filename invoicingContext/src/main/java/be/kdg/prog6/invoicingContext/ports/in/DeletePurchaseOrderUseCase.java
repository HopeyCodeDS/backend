package be.kdg.prog6.invoicingContext.ports.in;

import java.util.UUID;

public interface DeletePurchaseOrderUseCase {
    void deletePurchaseOrder(UUID purchaseOrderId);
}
