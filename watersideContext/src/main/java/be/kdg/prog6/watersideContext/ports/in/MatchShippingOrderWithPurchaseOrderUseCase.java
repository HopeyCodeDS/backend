package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.MatchShippingOrderWithPurchaseOrderCommand;

public interface MatchShippingOrderWithPurchaseOrderUseCase {
    ShippingOrder matchShippingOrderWithPurchaseOrder(MatchShippingOrderWithPurchaseOrderCommand command);
} 