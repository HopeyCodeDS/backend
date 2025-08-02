package be.kdg.prog6.watersideContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ShippingOrder {
    private UUID shippingOrderId;
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

    public ShippingOrder(String shippingOrderNumber, String purchaseOrderReference, 
                        String vesselNumber, String customerNumber,
                        LocalDateTime estimatedArrivalDate, LocalDateTime estimatedDepartureDate) {
        this.shippingOrderId = UUID.randomUUID();
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

    public enum ShippingOrderStatus {
        ARRIVED, INSPECTING, BUNKERING, LOADING, DEPARTED
    }
} 