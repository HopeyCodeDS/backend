package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;

@Getter
public class PurchaseOrderValidationResult {
    private final boolean isValid;
    private final String purchaseOrderReference;
    private final String customerNumber;
    private final String customerName;
    private final String validationMessage;

    public PurchaseOrderValidationResult(boolean isValid, String purchaseOrderReference, 
                                       String customerNumber, String customerName, String validationMessage) {
        this.isValid = isValid;
        this.purchaseOrderReference = purchaseOrderReference;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.validationMessage = validationMessage;
    }
} 