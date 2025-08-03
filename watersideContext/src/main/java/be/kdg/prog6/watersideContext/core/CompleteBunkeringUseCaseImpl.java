package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteBunkeringCommand;
import be.kdg.prog6.watersideContext.ports.in.CompleteBunkeringUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompleteBunkeringUseCaseImpl implements CompleteBunkeringUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public ShippingOrder completeBunkering(CompleteBunkeringCommand command) {
        log.info("Bunkering officer completing bunkering for shipping order: {}", command.getShippingOrderId());
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findById(command.getShippingOrderId());
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + command.getShippingOrderId());
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();

        // Check if shipping order is validated before allowing bunkering
        if (!shippingOrder.canPerformOperations()) {
            throw new IllegalStateException("Cannot complete bunkering. Shipping order must be validated by foreman first. " +
                    "Current status: " + shippingOrder.getStatus());
        }
        
        // Complete the bunkering
        shippingOrder.getBunkeringOperation().completeBunkering(command.getBunkeringOfficerSignature());
        
        // Update shipping order status if inspection is also completed
        if (shippingOrder.getInspectionOperation().isCompleted()) {
            shippingOrder.markAsReadyForLoading();
        }
        
        // Save the updated shipping order
        shippingOrderRepositoryPort.save(shippingOrder);
        
        log.info("Bunkering completed successfully for shipping order: {} by officer: {}", 
                command.getShippingOrderId(), command.getBunkeringOfficerSignature());
        
        return shippingOrder;
    }
} 