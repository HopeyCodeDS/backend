package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.UpdateShippingOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.UpdateShippingOrderUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateShippingOrderUseCaseImpl implements UpdateShippingOrderUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public ShippingOrder updateShippingOrder(UUID shippingOrderId, UpdateShippingOrderCommand command) {
        log.info("Updating shipping order: {}", shippingOrderId);
        
        var shippingOrderOpt = shippingOrderRepositoryPort.findById(shippingOrderId);
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + shippingOrderId);
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Update fields if provided
        if (command.getShippingOrderNumber() != null) {
            shippingOrder.setShippingOrderNumber(command.getShippingOrderNumber());
        }
        if (command.getPurchaseOrderReference() != null) {
            shippingOrder.setPurchaseOrderReference(command.getPurchaseOrderReference());
        }
        if (command.getVesselNumber() != null) {
            shippingOrder.setVesselNumber(command.getVesselNumber());
        }
        if (command.getCustomerNumber() != null) {
            shippingOrder.setCustomerNumber(command.getCustomerNumber());
        }
        if (command.getEstimatedArrivalDate() != null) {
            shippingOrder.setEstimatedArrivalDate(command.getEstimatedArrivalDate());
        }
        if (command.getEstimatedDepartureDate() != null) {
            shippingOrder.setEstimatedDepartureDate(command.getEstimatedDepartureDate());
        }
        if (command.getActualArrivalDate() != null) {
            shippingOrder.setActualArrivalDate(command.getActualArrivalDate());
        }
        if (command.getActualDepartureDate() != null) {
            shippingOrder.setActualDepartureDate(command.getActualDepartureDate());
        }
        
        shippingOrderRepositoryPort.save(shippingOrder);
        log.info("Shipping order updated successfully: {}", shippingOrderId);
        
        return shippingOrder;
    }
    
    @Override
    public void deleteShippingOrder(UUID shippingOrderId) {
        log.info("Deleting shipping order: {}", shippingOrderId);
        
        var shippingOrderOpt = shippingOrderRepositoryPort.findById(shippingOrderId);
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + shippingOrderId);
        }
        
        // In a real application, you might want to implement soft delete
        // For now, we'll just log the deletion
        log.info("Shipping order marked for deletion: {}", shippingOrderId);
    }
}
