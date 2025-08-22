package be.kdg.prog6.watersideContext.ports.out;

import be.kdg.prog6.common.events.PurchaseOrderMatchedWithShippingOrderEvent;

public interface PurchaseOrderMatchedEventPublisherPort {
    void publishPurchaseOrderMatchedEvent(PurchaseOrderMatchedWithShippingOrderEvent event);
} 