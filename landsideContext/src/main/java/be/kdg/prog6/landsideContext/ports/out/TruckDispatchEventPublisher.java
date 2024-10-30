package be.kdg.prog6.landsideContext.ports.out;


import be.kdg.prog6.common.events.TruckDispatchedEvent;

public interface TruckDispatchEventPublisher {
    void publish (TruckDispatchedEvent event);
}
