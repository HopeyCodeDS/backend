package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UnmatchedShippingOrderDto {
    private UUID shippingOrderId;
    private String shippingOrderNumber;
    private String purchaseOrderReference;
    private String vesselNumber;
    private String customerNumber;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime actualArrivalDate;
    private String status;
}