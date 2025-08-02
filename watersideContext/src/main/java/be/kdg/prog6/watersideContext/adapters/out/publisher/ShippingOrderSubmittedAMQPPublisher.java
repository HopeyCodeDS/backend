package be.kdg.prog6.watersideContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.ShippingOrderSubmitted;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderSubmittedPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;
import be.kdg.prog6.watersideContext.adapters.config.RabbitMQModuleTopology;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShippingOrderSubmittedAMQPPublisher implements ShippingOrderSubmittedPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public void publishShippingOrderSubmitted(ShippingOrder shippingOrder) {
        try {
            // create the event message
            ShippingOrderSubmitted event = new ShippingOrderSubmitted(
                shippingOrder.getShippingOrderNumber(),
                shippingOrder.getPurchaseOrderReference(), 
                shippingOrder.getVesselNumber(),
                shippingOrder.getCustomerNumber(),
                shippingOrder.getEstimatedArrivalDate(),
                shippingOrder.getEstimatedDepartureDate()
            );
            
            EventMessage eventMessage = EventMessage.builder()
                .eventHeader(EventHeader.builder()
                    .eventID(UUID.randomUUID())
                    .eventCatalog(EventCatalog.SHIPPING_ORDER_SUBMITTED)
                    .build())
                .eventBody(objectMapper.writeValueAsString(event))
                .build();
            
            rabbitTemplate.convertAndSend(RabbitMQModuleTopology.WATERSIDE_EVENTS_FAN_OUT, "shipping.order.submitted", eventMessage);
            
            log.info("Published ShippingOrderSubmitted event for SO: {} with vessel: {}", 
                    shippingOrder.getShippingOrderNumber(), shippingOrder.getVesselNumber());
                    
        } catch (Exception e) {
            log.error("Failed to publish ShippingOrderSubmitted event", e);
        }
    }
}