package be.kdg.prog6.watersideContext.ports.out;

// import be.kdg.prog6.watersideContext.domain.events.ShipReadyForLoadingEvent;
import be.kdg.prog6.common.events.ShipReadyForLoadingEvent;

public interface ShipReadyForLoadingEventPublisherPort {
    void publishShipReadyForLoadingEvent(ShipReadyForLoadingEvent event);
} 