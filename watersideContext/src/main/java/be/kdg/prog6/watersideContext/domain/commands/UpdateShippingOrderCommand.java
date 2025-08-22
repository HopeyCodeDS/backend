package be.kdg.prog6.watersideContext.domain.commands;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UpdateShippingOrderCommand {
    private String shippingOrderNumber;
    private String purchaseOrderReference;
    private String vesselNumber;
    private String customerNumber;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime estimatedDepartureDate;
    private LocalDateTime actualArrivalDate;
    private LocalDateTime actualDepartureDate;
}
