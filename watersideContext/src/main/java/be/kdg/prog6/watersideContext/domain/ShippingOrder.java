package be.kdg.prog6.watersideContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import be.kdg.prog6.common.events.ShipReadyForLoadingEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ShippingOrder {
    private final UUID shippingOrderId;
    private String shippingOrderNumber;
    private String purchaseOrderReference;
    private String vesselNumber;
    private String customerNumber;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime estimatedArrivalDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime estimatedDepartureDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime actualArrivalDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime actualDepartureDate;
    private ShippingOrderStatus status;
    private InspectionOperation inspectionOperation;
    private BunkeringOperation bunkeringOperation;
    private String foremanSignature;
    private LocalDateTime validationDate;
    private final List<Object> domainEvents = new ArrayList<>();

    public ShippingOrder(UUID shippingOrderId, String shippingOrderNumber, String purchaseOrderReference, 
                        String vesselNumber, String customerNumber,
                        LocalDateTime estimatedArrivalDate, LocalDateTime estimatedDepartureDate,
                        LocalDateTime actualArrivalDate) {
        this.shippingOrderId = shippingOrderId;
        this.shippingOrderNumber = shippingOrderNumber;
        this.purchaseOrderReference = purchaseOrderReference;
        this.vesselNumber = vesselNumber;
        this.customerNumber = customerNumber;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.estimatedDepartureDate = estimatedDepartureDate;
        this.actualArrivalDate = actualArrivalDate;  // Ideally, this should be set to the current date and time
        this.actualDepartureDate = null;
        this.status = ShippingOrderStatus.ARRIVED;
        this.inspectionOperation = new InspectionOperation(actualArrivalDate);
        this.bunkeringOperation = new BunkeringOperation(actualArrivalDate);
    }

    public void markAsArrived(LocalDateTime actualArrivalDate) {
        this.actualArrivalDate = actualArrivalDate;
        this.status = ShippingOrderStatus.ARRIVED;
        this.inspectionOperation.setPlannedDate(actualArrivalDate);
        this.bunkeringOperation.setPlannedDate(actualArrivalDate);
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

            // Domain object creates its own event
            ShipReadyForLoadingEvent event = new ShipReadyForLoadingEvent(
                this.shippingOrderId,
                this.purchaseOrderReference,
                this.vesselNumber,
                this.customerNumber
            );
            this.domainEvents.add(event);  // I want to use this as a store event for later publishing
            
        } else {
            throw new IllegalStateException("Cannot mark as ready for loading. " +
                "Status must be VALIDATED and both inspection and bunkering must be completed. " +
                "Current status: " + this.status + 
                ", Inspection completed: " + this.inspectionOperation.isCompleted() + 
                ", Bunkering completed: " + this.bunkeringOperation.isCompleted());
        }
    }

    // Methods to handle domain events
    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void markAsLoadingCompleted() {
        if (this.status == ShippingOrderStatus.READY_FOR_LOADING) {
            this.status = ShippingOrderStatus.LOADING_COMPLETED;
        } else {
            throw new IllegalStateException("Cannot mark as loading. Current status: " + this.status);
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
        ARRIVED, VALIDATED, INSPECTING, BUNKERING, READY_FOR_LOADING, LOADING_COMPLETED, DEPARTED
    }
} 