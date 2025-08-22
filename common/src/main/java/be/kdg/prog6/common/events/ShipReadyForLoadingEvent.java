package be.kdg.prog6.common.events;

import lombok.Getter;
import java.util.UUID;

@Getter
public class ShipReadyForLoadingEvent {
    private final UUID shippingOrderId;
    private final String purchaseOrderReference;
    private final String vesselNumber;
    private final String customerNumber;
    
    public ShipReadyForLoadingEvent(UUID shippingOrderId, String purchaseOrderReference, 
                                   String vesselNumber, String customerNumber) {
        this.shippingOrderId = shippingOrderId;
        this.purchaseOrderReference = purchaseOrderReference;
        this.vesselNumber = vesselNumber;
        this.customerNumber = customerNumber;
    }
} 