package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.CompleteLoadingUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShipDepartedEventPublisherPort;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import be.kdg.prog6.common.events.ShipDepartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompleteLoadingUseCaseImpl implements CompleteLoadingUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    private final ShipDepartedEventPublisherPort shipDepartedEventPublisherPort;
    
    @Override
    @Transactional
    public void completeLoading(UUID shippingOrderId, LocalDateTime loadingCompletionDate) {
        log.info("Completing loading for shipping order: {}", shippingOrderId);
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findById(shippingOrderId);
        
        if (shippingOrderOpt.isEmpty()) {
            log.warn("No shipping order found for ID: {}", shippingOrderId);
            return;
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Check if ship is ready for loading
        if (shippingOrder.getStatus() != ShippingOrder.ShippingOrderStatus.READY_FOR_LOADING) {
            log.warn("Ship is not ready for loading. Current status: {}", shippingOrder.getStatus());
            return;
        }
        
        // Mark as loading completed
        shippingOrder.markAsLoadingCompleted();
        shippingOrderRepositoryPort.save(shippingOrder);
        
        log.info("Successfully marked shipping order {} as loading completed", shippingOrderId);
        log.info("Now {} can leave the port", shippingOrder.getVesselNumber());
        
        // Automatically mark as departed after loading completion
        shippingOrder.markAsDeparted(loadingCompletionDate);
        shippingOrderRepositoryPort.save(shippingOrder);
        
        // Publish departure event
        ShipDepartedEvent event = new ShipDepartedEvent(
            shippingOrder.getShippingOrderId(),
            shippingOrder.getPurchaseOrderReference(),
            shippingOrder.getVesselNumber(),
            loadingCompletionDate
        );
        shipDepartedEventPublisherPort.publishShipDepartedEvent(event);

        log.info("Successfully completed loading and marked ship with vessel number {} as departed.", 
            shippingOrder.getVesselNumber());
    }
} 