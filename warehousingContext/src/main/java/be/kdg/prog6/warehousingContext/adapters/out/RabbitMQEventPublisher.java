package be.kdg.prog6.warehousingContext.adapters.out;

import be.kdg.prog6.common.events.ConveyorBeltAssignedEvent;
import be.kdg.prog6.common.events.PayloadReceivedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishConveyorBeltAssignedEvent(ConveyorBeltAssignedEvent event) {
        try {
            String eventMessage = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("payload.exchange", "payload.conveyorBelt.assigned", eventMessage);
            System.out.println("Published ConveyorBeltAssignedEvent: " + eventMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void publishPayloadDeliveredEvent(PayloadReceivedEvent event) {
        try {
            String eventMessage = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("payload.exchange", "payload.delivery.completed", eventMessage);
            System.out.println("Published PayloadReceivedEvent: " + eventMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
