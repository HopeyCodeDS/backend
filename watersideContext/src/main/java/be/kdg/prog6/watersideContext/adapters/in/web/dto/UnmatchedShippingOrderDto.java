package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime estimatedArrivalDate;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime actualArrivalDate;
    private String status;
}