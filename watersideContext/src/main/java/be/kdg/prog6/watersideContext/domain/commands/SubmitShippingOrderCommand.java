package be.kdg.prog6.watersideContext.domain.commands;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class SubmitShippingOrderCommand {
    private final UUID shippingOrderId;
    private final String shippingOrderNumber;
    private final String purchaseOrderReference;
    private final String vesselNumber;
    private final String customerNumber;
    private final LocalDateTime estimatedArrivalDate;
    private final LocalDateTime estimatedDepartureDate;

    public SubmitShippingOrderCommand(UUID shippingOrderId, String shippingOrderNumber, String purchaseOrderReference,
                                    String vesselNumber, String customerNumber,
                                    LocalDateTime estimatedArrivalDate, LocalDateTime estimatedDepartureDate) {
        this.shippingOrderId = shippingOrderId;
        this.shippingOrderNumber = shippingOrderNumber;
        this.purchaseOrderReference = purchaseOrderReference;
        this.vesselNumber = vesselNumber;
        this.customerNumber = customerNumber;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.estimatedDepartureDate = estimatedDepartureDate;
    }
} 