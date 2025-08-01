package be.kdg.prog6.invoicingContext.domain.commands;

import lombok.Getter;
import java.util.List;

@Getter
public class SubmitPurchaseOrderCommand {
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final List<PurchaseOrderLineCommand> orderLines;

    public SubmitPurchaseOrderCommand(String purchaseOrderNumber, String customerNumber, String customerName, List<PurchaseOrderLineCommand> orderLines) {
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.orderLines = orderLines;
    }

    @Getter
    public static class PurchaseOrderLineCommand {
        private final String rawMaterialName;
        private final double amountInTons;
        private final double pricePerTon;

        public PurchaseOrderLineCommand(String rawMaterialName, double amountInTons, double pricePerTon) {
            this.rawMaterialName = rawMaterialName;
            this.amountInTons = amountInTons;
            this.pricePerTon = pricePerTon;
        }
    }
} 