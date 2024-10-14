package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.common.commands.DockingCommand;
import be.kdg.prog6.landsideContext.ports.out.DockTruckPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class DockTruckPublisherAdapter implements DockTruckPublisherPort {

    private final static Logger log = LoggerFactory.getLogger(DockTruckPublisherAdapter.class);

    private final RabbitTemplate rabbitTemplate;

    public DockTruckPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishDockingEvent(DockingCommand command) {
        rabbitTemplate.convertAndSend("dockExchange", "dock.routingKey", command);
    }
}
