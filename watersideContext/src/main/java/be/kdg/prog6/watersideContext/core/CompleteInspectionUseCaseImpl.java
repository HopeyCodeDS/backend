package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteInspectionCommand;
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
    
    @Override
    public ShippingOrder completeInspection(CompleteInspectionCommand command) {
        log.info("Inspector completing inspection for shipping order: {}", command.getShippingOrderId());
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findById(command.getShippingOrderId());
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + command.getShippingOrderId());
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Complete the inspection
        shippingOrder.getInspectionOperation().completeInspection(command.getInspectorSignature());
        
        // Update shipping order status if bunkering is also completed
        if (shippingOrder.getBunkeringOperation().isCompleted()) {
            shippingOrder.markAsReadyForLoading();
        }
        
        // Save the updated shipping order
        shippingOrderRepositoryPort.save(shippingOrder);
        
        log.info("Inspection completed successfully for shipping order: {} by inspector: {}", 
                command.getShippingOrderId(), command.getInspectorSignature());
        
        return shippingOrder;
    }
} 