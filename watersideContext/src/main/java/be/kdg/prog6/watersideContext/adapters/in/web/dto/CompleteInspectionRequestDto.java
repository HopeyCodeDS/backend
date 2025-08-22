package be.kdg.prog6.watersideContext.adapters.in.web.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CompleteInspectionRequestDto {
    private UUID shippingOrderId;
    private String inspectorSignature;
} 