package be.kdg.prog6.landsideContext.adapters.out.publisher;

import be.kdg.prog6.common.commands.DockingCommand;
import be.kdg.prog6.landsideContext.ports.out.DockTruckPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class DockTruckPublisher implements DockTruckPublisherPort {

    private final static Logger log = LoggerFactory.getLogger(DockTruckPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public DockTruckPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishDockingEvent(DockingCommand command) {
        log.info("Publishing message about docking to the correct conveyor belt");
        this.rabbitTemplate.convertAndSend("dockExchange", "dock.routingKey", command);
    }
}
