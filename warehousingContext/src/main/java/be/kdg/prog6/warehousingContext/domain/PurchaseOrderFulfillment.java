package be.kdg.prog6.warehousingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PurchaseOrderFulfillment {
    private final UUID fulfillmentId;
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final LocalDateTime orderDate;
    private final double totalValue;
    private FulfillmentStatus status;
    private LocalDateTime fulfillmentDate;
    private String vesselNumber; // Ship that fulfilled the order

    public PurchaseOrderFulfillment(String purchaseOrderNumber, String customerNumber, 
                                   String customerName, double totalValue, LocalDateTime orderDate) {
        this.fulfillmentId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalValue = totalValue;
        this.status = FulfillmentStatus.OUTSTANDING;
        this.fulfillmentDate = null;
        this.vesselNumber = null;
    }

    public void markAsFulfilled(String vesselNumber) {
        this.status = FulfillmentStatus.FULFILLED;
        this.fulfillmentDate = LocalDateTime.now();
        this.vesselNumber = vesselNumber;
    }

    public enum FulfillmentStatus {
        OUTSTANDING, FULFILLED
    }
} 