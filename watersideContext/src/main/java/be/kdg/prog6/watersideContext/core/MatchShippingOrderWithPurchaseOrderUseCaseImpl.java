package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.common.events.PurchaseOrderMatchedWithShippingOrderEvent;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.MatchShippingOrderWithPurchaseOrderCommand;
import be.kdg.prog6.watersideContext.ports.in.MatchShippingOrderWithPurchaseOrderUseCase;
import be.kdg.prog6.watersideContext.ports.out.PurchaseOrderMatchedEventPublisherPort;
import be.kdg.prog6.watersideContext.ports.out.PurchaseOrderValidationResult;
import be.kdg.prog6.watersideContext.ports.out.PurchaseOrderValidationPort;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchShippingOrderWithPurchaseOrderUseCaseImpl implements MatchShippingOrderWithPurchaseOrderUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    private final PurchaseOrderValidationPort purchaseOrderValidationPort;
    private final PurchaseOrderMatchedEventPublisherPort purchaseOrderMatchedEventPublisherPort;
    
    @Override
    public ShippingOrder matchShippingOrderWithPurchaseOrder(MatchShippingOrderWithPurchaseOrderCommand command) {
        log.info("Foreman matching shipping order with purchase order: {}", command.getShippingOrderId());
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findById(command.getShippingOrderId());
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Shipping order not found: " + command.getShippingOrderId());
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Validate the purchase order reference
        Optional<PurchaseOrderValidationResult> validationResult = purchaseOrderValidationPort
            .validatePurchaseOrder(shippingOrder.getPurchaseOrderReference(), shippingOrder.getCustomerNumber());
        
        if (validationResult.isEmpty()) {
            throw new IllegalArgumentException("Purchase order not found: " + shippingOrder.getPurchaseOrderReference());
        }
        
        PurchaseOrderValidationResult result = validationResult.get();
        
        if (!result.isValid()) {
            throw new IllegalArgumentException("Purchase order validation failed: " + result.getValidationMessage());
        }
        
        // Verify customer number matches
        if (!result.getCustomerNumber().equals(shippingOrder.getCustomerNumber())) {
            throw new IllegalArgumentException("Customer number mismatch. SO: " + shippingOrder.getCustomerNumber() + 
                                             ", PO: " + result.getCustomerNumber());
        }
        
        // Mark shipping order as validated
        shippingOrder.markAsValidated(command.getForemanSignature());
        
        // Save the updated shipping order
        shippingOrderRepositoryPort.save(shippingOrder);

        // Publish event to notify WarehousingContext about the PO-SO matching
        PurchaseOrderMatchedWithShippingOrderEvent event = new PurchaseOrderMatchedWithShippingOrderEvent(
            shippingOrder.getPurchaseOrderReference(),
            shippingOrder.getShippingOrderNumber(),
            shippingOrder.getVesselNumber(),
            shippingOrder.getCustomerNumber(),
            LocalDateTime.now()
        );

        purchaseOrderMatchedEventPublisherPort.publishPurchaseOrderMatchedEvent(event);
        log.info("Published PurchaseOrderMatchedWithShippingOrderEvent for PO: {} with vessel: {}", 
                shippingOrder.getPurchaseOrderReference(), shippingOrder.getVesselNumber());
        
        log.info("Shipping order successfully matched with purchase order: {} by foreman: {}", 
                command.getShippingOrderId(), command.getForemanSignature());
        
        return shippingOrder;
    }
} 