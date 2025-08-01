package be.kdg.prog6.invoicingContext.domain;

import lombok.Getter;

@Getter
public class PurchaseOrderLine {
    private final String rawMaterialName;
    private final double amountInTons;
    private final double pricePerTon;
    private final double lineTotal;

    public PurchaseOrderLine(String rawMaterialName, double amountInTons, double pricePerTon) {
        this.rawMaterialName = rawMaterialName;
        this.amountInTons = amountInTons;
        this.pricePerTon = pricePerTon;
        this.lineTotal = amountInTons * pricePerTon;
    }
} 