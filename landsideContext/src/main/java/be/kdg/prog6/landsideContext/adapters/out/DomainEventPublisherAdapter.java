package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.common.events.WeighBridgeTicketGeneratedEvent;
import be.kdg.prog6.common.events.WeighingBridgeAssignedEvent;
import be.kdg.prog6.landsideContext.domain.events.WarehouseAssignedEvent;
import be.kdg.prog6.landsideContext.ports.out.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisherAdapter implements DomainEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(DomainEventPublisherAdapter.class);

    @Override
    public void publish(WeighingBridgeAssignedEvent event) {
        log.info("Publishing WeighingBridgeAssignedEvent for license plate: {}", event.licensePlate());
        // Here, send the event through RabbitMQ, Kafka, or another messaging service
    }

    @Override
    public void publish(WarehouseAssignedEvent event) {
        log.info("Publishing WarehouseAssignedEvent for license plate: {}", event.licensePlate());

    }

    @Override
    public void publish(WeighBridgeTicketGeneratedEvent event) {
        log.info("Publishing WeighBridgeTicketGeneratedEvent for truck with license plate: {}", event.getLicensePlate());
    }
}
