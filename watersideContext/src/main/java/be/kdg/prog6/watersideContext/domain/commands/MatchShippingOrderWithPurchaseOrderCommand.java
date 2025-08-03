package be.kdg.prog6.watersideContext.domain.commands;

import lombok.Getter;
import java.util.UUID;

@Getter
public class MatchShippingOrderWithPurchaseOrderCommand {
    private final UUID shippingOrderId;
    private final String foremanSignature;

    public MatchShippingOrderWithPurchaseOrderCommand(UUID shippingOrderId, String foremanSignature) {
        this.shippingOrderId = shippingOrderId;
        this.foremanSignature = foremanSignature;
    }
} 