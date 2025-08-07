package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime plannedInspectionDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime actualArrivalDate;
    private String status;
} 