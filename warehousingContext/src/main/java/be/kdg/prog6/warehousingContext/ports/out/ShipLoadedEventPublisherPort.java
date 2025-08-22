package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.common.events.ShipLoadedEvent;

public interface ShipLoadedEventPublisherPort {
    void publishShipLoadedEvent(ShipLoadedEvent event);
} 