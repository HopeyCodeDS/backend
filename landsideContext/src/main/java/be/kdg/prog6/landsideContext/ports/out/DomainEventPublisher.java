package be.kdg.prog6.landsideContext.ports.out;


import be.kdg.prog6.common.events.WeighBridgeTicketGeneratedEvent;
import be.kdg.prog6.common.events.WeighingBridgeAssignedEvent;
import be.kdg.prog6.landsideContext.domain.events.WarehouseAssignedEvent;

public interface DomainEventPublisher {
    void publish (WeighingBridgeAssignedEvent event);
    void publish (WarehouseAssignedEvent event);
    void publish(WeighBridgeTicketGeneratedEvent event);
}
