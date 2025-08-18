package be.kdg.prog6.invoicingContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class SubmitPurchaseOrderCommand {
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final UUID sellerId;
    private final String sellerName;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime orderDate;
    private final List<PurchaseOrderLineCommand> orderLines;

    public SubmitPurchaseOrderCommand(String purchaseOrderNumber, String customerNumber, String customerName, UUID sellerId, String sellerName, LocalDateTime orderDate, List<PurchaseOrderLineCommand> orderLines) {
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
    }

    @Getter
    public static class PurchaseOrderLineCommand {
        private final int lineNumber;
        private final String rawMaterialName;
        private final double amountInTons;
        private final double pricePerTon;

        public PurchaseOrderLineCommand(int lineNumber, String rawMaterialName, double amountInTons, double pricePerTon) {
            this.lineNumber = lineNumber;
            this.rawMaterialName = rawMaterialName;
            this.amountInTons = amountInTons;
            this.pricePerTon = pricePerTon;
        }
    }
} 