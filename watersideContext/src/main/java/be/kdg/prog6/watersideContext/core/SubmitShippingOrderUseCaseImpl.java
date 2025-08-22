package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.SubmitShippingOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.SubmitShippingOrderUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderSubmittedPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmitShippingOrderUseCaseImpl implements SubmitShippingOrderUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    private final ShippingOrderSubmittedPort shippingOrderSubmittedPort;
    
    @Override
    public ShippingOrder submitShippingOrder(SubmitShippingOrderCommand command) {
        log.info("Submitting shipping order: {} for vessel: {}", 
                command.getShippingOrderNumber(), command.getVesselNumber());
        
        // Create shipping order
        ShippingOrder shippingOrder = new ShippingOrder(
            command.getShippingOrderId(),
            command.getShippingOrderNumber(),
            command.getPurchaseOrderReference(),
            command.getVesselNumber(),
            command.getCustomerNumber(),
            command.getEstimatedArrivalDate(),
            command.getEstimatedDepartureDate(),
            command.getActualArrivalDate()
        );
        
        // Mark as arrived
        shippingOrder.markAsArrived(command.getActualArrivalDate());
        
        // Save shipping order
        shippingOrderRepositoryPort.save(shippingOrder);
        log.info("Shipping order saved in the database");
        
        // Publish event
        shippingOrderSubmittedPort.publishShippingOrderSubmitted(shippingOrder);
        
        log.info("Shipping order submitted successfully: {}", shippingOrder.getShippingOrderId());
        
        return shippingOrder;
    }
} 