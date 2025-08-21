package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteInspectionCommand;
import be.kdg.prog6.common.events.ShipReadyForLoadingEvent;
// import be.kdg.prog6.watersideContext.domain.events.ShipReadyForLoadingEvent;
import be.kdg.prog6.watersideContext.ports.out.ShipReadyForLoadingEventPublisherPort;
import be.kdg.prog6.watersideContext.ports.in.CompleteInspectionUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompleteInspectionUseCaseImpl implements CompleteInspectionUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    private final ShipReadyForLoadingEventPublisherPort shipReadyForLoadingEventPublisherPort;

    @Override
    public ShippingOrder completeInspection(CompleteInspectionCommand command) {
        log.info("Inspector completing inspection for shipping order: {}", command.shippingOrderId());
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findById(command.shippingOrderId());
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + command.shippingOrderId());
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Check if shipping order is validated before allowing inspection
        if (!shippingOrder.canPerformOperations()) {
            throw new IllegalStateException("Cannot complete inspection. Shipping order must be validated by foreman first. " +
                    "Current status: " + shippingOrder.getStatus());
        }
        
        // Complete the inspection
        shippingOrder.getInspectionOperation().completeInspection(command.inspectorSignature());
        
        // Update shipping order status if bunkering is also completed
        if (shippingOrder.getBunkeringOperation().isCompleted()) {
            shippingOrder.markAsReadyForLoading();
        }

        // Save the updated shipping order
        shippingOrderRepositoryPort.save(shippingOrder);

        // PUBLISH EVENT for automatic loading
        shippingOrder.getDomainEvents().forEach(event -> {
            if (event instanceof ShipReadyForLoadingEvent) {
                shipReadyForLoadingEventPublisherPort.publishShipReadyForLoadingEvent(
                    (ShipReadyForLoadingEvent) event);
            }
        });
        shippingOrder.clearDomainEvents();
        
        
        log.info("Inspection completed successfully for shipping order: {} by inspector: {}", 
                command.shippingOrderId(), command.inspectorSignature());
        
        return shippingOrder;
    }
} 