package be.kdg.prog6.invoicingContext.adapters.out.publisher;

import be.kdg.prog6.common.events.EventCatalog;
import be.kdg.prog6.common.events.EventHeader;
import be.kdg.prog6.common.events.EventMessage;
import be.kdg.prog6.common.events.PurchaseOrderSubmitted;
import be.kdg.prog6.invoicingContext.adapters.config.RabbitMQModuleTopology;
import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.ports.out.PurchaseOrderSubmittedPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderSubmittedAMQPPublisher implements PurchaseOrderSubmittedPort {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;     
    
    @Override
    public void publishPurchaseOrderSubmitted(PurchaseOrder purchaseOrder) {
        try {
            // Create the event using shared kernel
            PurchaseOrderSubmitted event = toCommonEvent(purchaseOrder);
            
            // Create event message with header
            EventMessage eventMessage = EventMessage.builder()
                    .eventHeader(EventHeader.builder()
                            .eventID(UUID.randomUUID())
                            .eventCatalog(EventCatalog.PURCHASE_ORDER_SUBMITTED)
                            .build())
                    .eventBody(objectMapper.writeValueAsString(event))
                    .build();
            
            // Publish to RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQModuleTopology.PURCHASE_ORDER_SUBMITTED_FAN_OUT, "purchase.order.submitted", eventMessage);
            
            log.info("Published PurchaseOrderSubmitted event for PO: {} with total value: ${}", 
                    purchaseOrder.getPurchaseOrderNumber(), purchaseOrder.getTotalValue());
                    
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize PurchaseOrderSubmitted event for PO: {}", 
                    purchaseOrder.getPurchaseOrderNumber(), e);
        } catch (Exception e) {
            log.error("Failed to publish PurchaseOrderSubmitted event for PO: {}", 
                    purchaseOrder.getPurchaseOrderNumber(), e);
        }
    }
    
    private PurchaseOrderSubmitted toCommonEvent(PurchaseOrder purchaseOrder) {
        List<PurchaseOrderSubmitted.PurchaseOrderLine> lineEvents = 
                purchaseOrder.getOrderLines().stream()
                        .map(line -> new PurchaseOrderSubmitted.PurchaseOrderLine(
                                line.getRawMaterialName(),
                                line.getAmountInTons(),
                                line.getPricePerTon()))
                        .collect(Collectors.toList());
        
        return new PurchaseOrderSubmitted(
                purchaseOrder.getPurchaseOrderId(),
                purchaseOrder.getPurchaseOrderNumber(),
                purchaseOrder.getCustomerNumber(),
                purchaseOrder.getCustomerName(),
                purchaseOrder.getTotalValue(),
                lineEvents,
                purchaseOrder.getOrderDate()
        );
    }
} 