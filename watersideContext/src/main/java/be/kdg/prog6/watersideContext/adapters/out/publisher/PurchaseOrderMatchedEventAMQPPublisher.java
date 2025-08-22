package be.kdg.prog6.watersideContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderMatchedWithShippingOrderEvent;
import be.kdg.prog6.watersideContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.watersideContext.ports.out.PurchaseOrderMatchedEventPublisherPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderMatchedEventAMQPPublisher implements PurchaseOrderMatchedEventPublisherPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    @Override
    public void publishPurchaseOrderMatchedEvent(PurchaseOrderMatchedWithShippingOrderEvent event) {
        log.info("Publishing PurchaseOrderMatchedWithShippingOrderEvent for PO: {} with vessel: {}", 
                event.purchaseOrderReference(), event.vesselNumber());

        try {
            EventHeader header = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER)
                .build();

            // Serialize the inner event body to a string
            String eventBodyJson = objectMapper.writeValueAsString(event);

            // Create the EventMessage object
            EventMessage eventMessage = EventMessage.builder()
                .eventHeader(header)
                .eventBody(eventBodyJson)
                .build();

            // Sending the EventMessage object directly
            rabbitTemplate.convertAndSend(RabbitMQModuleTopology.PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_FAN_OUT, "po-matched-with-so", eventMessage);
            
            log.info("Published PurchaseOrderMatchedWithShippingOrderEvent for PO: {} with vessel: {} to exchange: {}", 
                    event.purchaseOrderReference(), event.vesselNumber(), RabbitMQModuleTopology.PURCHASE_ORDER_MATCHED_WITH_SHIPPING_ORDER_FAN_OUT);
                    
        } catch (Exception e) {
            log.error("Failed to publish PurchaseOrderMatchedWithShippingOrderEvent for PO: {}", 
                    event.purchaseOrderReference(), e);
        }
    }
} 