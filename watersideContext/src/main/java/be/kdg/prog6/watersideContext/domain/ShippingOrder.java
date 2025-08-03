package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ShippingOrder {
    private final UUID shippingOrderId;
    private String shippingOrderNumber;
    private String purchaseOrderReference;
    private String vesselNumber;
    private String customerNumber;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime estimatedDepartureDate;
    private LocalDateTime actualArrivalDate;
    private LocalDateTime actualDepartureDate;
    private ShippingOrderStatus status;
    private final InspectionOperation inspectionOperation;
    private final BunkeringOperation bunkeringOperation;
    private String foremanSignature;
    private LocalDateTime validationDate;

    public ShippingOrder(UUID shippingOrderId, String shippingOrderNumber, String purchaseOrderReference, 
                        String vesselNumber, String customerNumber,
                        LocalDateTime estimatedArrivalDate, LocalDateTime estimatedDepartureDate) {
        this.shippingOrderId = shippingOrderId;
        this.shippingOrderNumber = shippingOrderNumber;
        this.purchaseOrderReference = purchaseOrderReference;
        this.vesselNumber = vesselNumber;
        this.customerNumber = customerNumber;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.estimatedDepartureDate = estimatedDepartureDate;
        this.actualArrivalDate = null;
        this.actualDepartureDate = null;
        this.status = ShippingOrderStatus.ARRIVED;
        this.inspectionOperation = new InspectionOperation();
        this.bunkeringOperation = new BunkeringOperation();
    }

    public void markAsArrived(LocalDateTime actualArrivalDate) {
        this.actualArrivalDate = actualArrivalDate;
        this.status = ShippingOrderStatus.ARRIVED;
    }

    public void markAsDeparted(LocalDateTime actualDepartureDate) {
        this.actualDepartureDate = actualDepartureDate;
        this.status = ShippingOrderStatus.DEPARTED;
    }

    public boolean isFulfilled() {
        return status == ShippingOrderStatus.DEPARTED && 
               inspectionOperation.isCompleted() && 
               bunkeringOperation.isCompleted();
    }

    public void markAsReadyForLoading() {
        // Only allow READY_FOR_LOADING if validated AND both operations completed
        if (this.status == ShippingOrderStatus.VALIDATED && 
            this.inspectionOperation.isCompleted() && 
            this.bunkeringOperation.isCompleted()) {
            this.status = ShippingOrderStatus.READY_FOR_LOADING;
        } else {
            throw new IllegalStateException("Cannot mark as ready for loading. " +
                "Status must be VALIDATED and both inspection and bunkering must be completed. " +
                "Current status: " + this.status + 
                ", Inspection completed: " + this.inspectionOperation.isCompleted() + 
                ", Bunkering completed: " + this.bunkeringOperation.isCompleted());
        }
    }

    public void markAsValidated(String foremanSignature) {
        this.foremanSignature = foremanSignature;
        this.validationDate = LocalDateTime.now();
        this.status = ShippingOrderStatus.VALIDATED;
    }

    // Helper method to check if operations can be performed
    public boolean canPerformOperations() {
        return this.status == ShippingOrderStatus.VALIDATED;
    }

    public enum ShippingOrderStatus {
        ARRIVED, VALIDATED, INSPECTING, BUNKERING, READY_FOR_LOADING, LOADING, DEPARTED
    }
} 