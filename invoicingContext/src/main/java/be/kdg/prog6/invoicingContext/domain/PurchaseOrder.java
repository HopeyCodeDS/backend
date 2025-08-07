package be.kdg.prog6.invoicingContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class PurchaseOrder {
    private final UUID purchaseOrderId;
    private final String purchaseOrderNumber;
    private final String customerNumber;  // Buyer
    private final String customerName;    // Buyer
    private final String sellerId;        // Seller
    private final String sellerName;      // Seller
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime orderDate;
    private PurchaseOrderStatus status;
    private final double totalValue;
    private final List<PurchaseOrderLine> orderLines;

    public PurchaseOrder(String purchaseOrderNumber, String customerNumber, String customerName, 
                        String sellerId, String sellerName, LocalDateTime orderDate, List<PurchaseOrderLine> orderLines) {
        this.purchaseOrderId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber; 
        this.customerName = customerName;      
        this.sellerId = sellerId;              
        this.sellerName = sellerName;          
        this.orderDate = orderDate;
        this.status = PurchaseOrderStatus.PENDING;
        this.orderLines = orderLines;
        this.totalValue = calculateTotalValue();
    }

    public PurchaseOrder(UUID purchaseOrderId, String purchaseOrderNumber, String customerNumber, String customerName, 
                    String sellerId, String sellerName, LocalDateTime orderDate, 
                    PurchaseOrderStatus status, List<PurchaseOrderLine> orderLines) {
        this.purchaseOrderId = purchaseOrderId;
    this.purchaseOrderNumber = purchaseOrderNumber;
    this.customerNumber = customerNumber; 
    this.customerName = customerName;      
    this.sellerId = sellerId;              
    this.sellerName = sellerName;          
    this.orderDate = orderDate;
    this.status = status;
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

    public void markAsFulfilled() {
        this.status = PurchaseOrderStatus.FULFILLED;
    }
} 