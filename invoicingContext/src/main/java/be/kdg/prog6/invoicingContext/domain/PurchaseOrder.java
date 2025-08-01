package be.kdg.prog6.invoicingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class PurchaseOrder {
    private final UUID purchaseOrderId;
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final LocalDateTime orderDate;
    private final PurchaseOrderStatus status;
    private final double totalValue;
    private final List<PurchaseOrderLine> orderLines;

    public PurchaseOrder(String purchaseOrderNumber, String customerNumber, String customerName, List<PurchaseOrderLine> orderLines) {
        this.purchaseOrderId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.orderDate = LocalDateTime.now();
        this.status = PurchaseOrderStatus.PENDING;
        this.orderLines = orderLines;
        this.totalValue = calculateTotalValue();
    }

    private double calculateTotalValue() {
        return orderLines.stream()
                .mapToDouble(PurchaseOrderLine::getLineTotal)
                .sum();
    }

    public enum PurchaseOrderStatus {
        PENDING, CONFIRMED, FULFILLED, CANCELLED
    }
} 