package be.kdg.prog6.warehousingContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PurchaseOrderFulfillmentTracking {
    private final UUID trackingId;
    private final String purchaseOrderNumber; // Reference to PO in InvoicingContext
    private final String customerNumber;
    private final String customerName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime orderDate;
    private final double totalValue;
    private FulfillmentStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fulfillmentDate;
    private String vesselNumber; // Ship that fulfilled the order

    // Constructor for new instances
    public PurchaseOrderFulfillmentTracking(String purchaseOrderNumber, String customerNumber, 
                                           String customerName, double totalValue, LocalDateTime orderDate) {
        this.trackingId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalValue = totalValue;
        this.status = FulfillmentStatus.OUTSTANDING;
        this.fulfillmentDate = null;
        this.vesselNumber = null;
    }

    // Constructor for reconstructing from database
    public PurchaseOrderFulfillmentTracking(UUID trackingId, String purchaseOrderNumber, String customerNumber, 
                                           String customerName, double totalValue, LocalDateTime orderDate,
                                           FulfillmentStatus status, LocalDateTime fulfillmentDate, String vesselNumber) {
        this.trackingId = trackingId;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalValue = totalValue;
        this.status = status;
        this.fulfillmentDate = fulfillmentDate;
        this.vesselNumber = vesselNumber;
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