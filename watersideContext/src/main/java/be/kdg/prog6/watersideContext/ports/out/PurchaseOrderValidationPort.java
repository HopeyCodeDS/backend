package be.kdg.prog6.watersideContext.ports.out;

import java.util.Optional;

import be.kdg.prog6.watersideContext.domain.PurchaseOrderValidationResult;

public interface PurchaseOrderValidationPort {
    Optional<PurchaseOrderValidationResult> validatePurchaseOrder(String purchaseOrderReference, String customerNumber);
} 