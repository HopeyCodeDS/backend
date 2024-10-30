package be.kdg.prog6.landsideContext.adapters.out.publisher;

import be.kdg.prog6.common.events.TruckDispatchedEvent;
import be.kdg.prog6.landsideContext.adapters.out.RabbitMQConfig;
import be.kdg.prog6.landsideContext.ports.out.TruckDispatchEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TruckDispatchPublisher implements TruckDispatchEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TruckDispatchPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(TruckDispatchedEvent event) {
        log.info("Publishing payload delivery event: {}", event.toString());
        try {
            // Serialize the event to JSON
            String eventMessage = objectMapper.writeValueAsString(event);

            // Publish to RabbitMQ with the defined exchange and routing key
            rabbitTemplate.convertAndSend(
                    "payload.exchange",
                    "payload.delivery.initiated",
                    eventMessage
            );

            System.out.println("Published TruckDispatchedEvent: " + eventMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
