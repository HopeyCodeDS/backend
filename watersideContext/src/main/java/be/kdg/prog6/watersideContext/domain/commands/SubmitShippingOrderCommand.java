package be.kdg.prog6.watersideContext.domain.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime estimatedArrivalDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime estimatedDepartureDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime actualArrivalDate;

    public SubmitShippingOrderCommand(UUID shippingOrderId, String shippingOrderNumber, String purchaseOrderReference,
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
        this.actualArrivalDate = actualArrivalDate;
    }
} 