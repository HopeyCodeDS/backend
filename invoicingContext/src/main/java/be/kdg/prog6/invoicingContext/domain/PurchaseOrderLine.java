package be.kdg.prog6.invoicingContext.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class PurchaseOrderLine {
    private final UUID lineId;
    private final int lineNumber;
    private final String rawMaterialName;
    private final double amountInTons;
    private final double pricePerTon;
    private final double lineTotal;

    // Constructor for new lines (creates new UUID)
    public PurchaseOrderLine(int lineNumber, String rawMaterialName, double amountInTons, double pricePerTon) {
        this.lineId = UUID.randomUUID();
        this.lineNumber = lineNumber;
        this.rawMaterialName = rawMaterialName;
        this.amountInTons = amountInTons;
        this.pricePerTon = pricePerTon;
        this.lineTotal = amountInTons * pricePerTon;
    }

    // Reconstruction constructor for existing lines (preserves UUID)
    public PurchaseOrderLine(UUID lineId, int lineNumber, String rawMaterialName, double amountInTons, double pricePerTon) {
        this.lineId = lineId;
        this.lineNumber = lineNumber;
        this.rawMaterialName = rawMaterialName;
        this.amountInTons = amountInTons;
        this.pricePerTon = pricePerTon;
        this.lineTotal = amountInTons * pricePerTon;
    }
} 