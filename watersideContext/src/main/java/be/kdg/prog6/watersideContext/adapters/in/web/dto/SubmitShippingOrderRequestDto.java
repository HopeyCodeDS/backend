package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubmitShippingOrderRequestDto {
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
}
