package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OutstandingInspectionDto {
    private UUID shippingOrderId;
    private String shippingOrderNumber;
    private String vesselNumber;
    private String purchaseOrderReference;
    private String customerNumber;
    private LocalDateTime plannedInspectionDate;
    private LocalDateTime actualArrivalDate;
    private String status;
} 