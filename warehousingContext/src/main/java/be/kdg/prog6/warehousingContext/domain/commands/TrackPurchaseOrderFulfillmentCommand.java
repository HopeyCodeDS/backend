package be.kdg.prog6.warehousingContext.domain.commands;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrackPurchaseOrderFulfillmentCommand {
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String customerName;
    private final double totalValue;
    private final LocalDateTime orderDate;
} 