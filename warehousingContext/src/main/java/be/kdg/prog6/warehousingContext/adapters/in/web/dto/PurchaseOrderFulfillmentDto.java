package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PurchaseOrderFulfillmentDto {
    private final String trackingId;
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final LocalDateTime orderDate;
    private final String status;
    private final double totalValue;
    private final LocalDateTime fulfillmentDate;
    private final String vesselNumber;
} 