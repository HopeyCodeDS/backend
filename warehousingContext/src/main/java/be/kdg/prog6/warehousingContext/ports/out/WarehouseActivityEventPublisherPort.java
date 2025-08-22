package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.common.events.WarehouseActivityEvent;

public interface WarehouseActivityEventPublisherPort {
    void publishWarehouseActivityEvent(WarehouseActivityEvent event);
} 