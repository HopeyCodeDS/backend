package be.kdg.prog6.watersideContext.ports.out;

import be.kdg.prog6.common.events.ShipDepartedEvent;

public interface ShipDepartedEventPublisherPort {
    void publishShipDepartedEvent(ShipDepartedEvent event);
} 