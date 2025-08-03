package be.kdg.prog6.watersideContext.ports.out;

import java.util.Optional;

public interface PurchaseOrderValidationPort {
    Optional<PurchaseOrderValidationResult> validatePurchaseOrder(String purchaseOrderReference, String customerNumber);
} 