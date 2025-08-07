package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class PurchaseOrderMaterialRequirement {
    private final UUID requirementId;
    private final String purchaseOrderNumber;
    private final String rawMaterialName;
    private final double requiredAmountInTons;
    private final double pricePerTon;
    private final double totalValue;
    private double fulfilledAmountInTons;
    private double remainingAmountInTons;

    public PurchaseOrderMaterialRequirement(String purchaseOrderNumber, String rawMaterialName, 
                                          double requiredAmountInTons, double pricePerTon) {
        this.requirementId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.rawMaterialName = rawMaterialName;
        this.requiredAmountInTons = requiredAmountInTons;
        this.pricePerTon = pricePerTon;
        this.totalValue = requiredAmountInTons * pricePerTon;
        this.fulfilledAmountInTons = 0.0;
        this.remainingAmountInTons = requiredAmountInTons;
    }

    // Constructor for reconstructing from database
    public PurchaseOrderMaterialRequirement(UUID requirementId, String purchaseOrderNumber, 
                                          String rawMaterialName, double requiredAmountInTons, 
                                          double pricePerTon, double fulfilledAmountInTons) {
        this.requirementId = requirementId;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.rawMaterialName = rawMaterialName;
        this.requiredAmountInTons = requiredAmountInTons;
        this.pricePerTon = pricePerTon;
        this.totalValue = requiredAmountInTons * pricePerTon;
        this.fulfilledAmountInTons = fulfilledAmountInTons;
        this.remainingAmountInTons = requiredAmountInTons - fulfilledAmountInTons;
    }

    public void fulfillAmount(double amountToFulfill) {
        double actualFulfillment = Math.min(amountToFulfill, remainingAmountInTons);
        this.fulfilledAmountInTons += actualFulfillment;
        this.remainingAmountInTons = requiredAmountInTons - fulfilledAmountInTons;
    }

    public boolean isFullyFulfilled() {
        return remainingAmountInTons <= 0;
    }

    public double getFulfillmentPercentage() {
        return (fulfilledAmountInTons / requiredAmountInTons) * 100.0;
    }
} 