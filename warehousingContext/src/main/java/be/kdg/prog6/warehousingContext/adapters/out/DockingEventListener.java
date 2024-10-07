package be.kdg.prog6.warehousingContext.adapters.out;

import be.kdg.prog6.common.events.DockingEvent;
import be.kdg.prog6.warehousingContext.core.DockingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class DockingEventListener {

    private static final Logger log = LoggerFactory.getLogger(DockingEventListener.class);

    private final DockingService dockingService;

    public DockingEventListener(DockingService dockingService) {
        this.dockingService = dockingService;
    }

    @RabbitListener(queues = "dockQueue", messageConverter = "#{jackson2JsonMessageConverter}")
    public void handleDockingEvent(DockingEvent event) {
        // Log the event reception
        log.info("Received Docking Event: {}", event);

        // Process the event using the DockingService
        dockingService.processDockingEvent(event);
    }
}
