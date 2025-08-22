package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.domain.PurchaseOrderMaterialRequirement;
import be.kdg.prog6.warehousingContext.domain.commands.TrackPurchaseOrderFulfillmentCommand;
import be.kdg.prog6.warehousingContext.ports.in.TrackPurchaseOrderFulfillmentUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderMaterialRequirementRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackPurchaseOrderFulfillmentUseCaseImpl implements TrackPurchaseOrderFulfillmentUseCase {

    private final PurchaseOrderFulfillmentRepositoryPort repositoryPort;
    private final PurchaseOrderMaterialRequirementRepositoryPort materialRequirementRepositoryPort;
    
    
    @Override
    @Transactional
    public void trackNewPurchaseOrder(TrackPurchaseOrderFulfillmentCommand command) {
        log.info("Tracking new purchase order for fulfillment: {}", command.getPurchaseOrderNumber());
        
        // Create the main fulfillment tracking
        PurchaseOrderFulfillmentTracking fulfillment = new PurchaseOrderFulfillmentTracking(
            command.getPurchaseOrderNumber(),
            command.getCustomerNumber(),
            command.getCustomerName(),
            command.getTotalValue(),
            command.getOrderDate()
        );
        
        repositoryPort.save(fulfillment);

        // Create material requirements for each orderline
        for (TrackPurchaseOrderFulfillmentCommand.OrderLine orderLine : command.getOrderLines()) {
            PurchaseOrderMaterialRequirement materialRequirement = new PurchaseOrderMaterialRequirement(
                command.getPurchaseOrderNumber(),
                orderLine.getRawMaterialName(),
                orderLine.getAmountInTons(),
                orderLine.getPricePerTon()
            );

            materialRequirementRepositoryPort.save(materialRequirement);

            log.info("Created material requirement for PO {}: {} tons of {} at ${}/ton", 
                command.getPurchaseOrderNumber(), 
                orderLine.getAmountInTons(), 
                orderLine.getRawMaterialName(), 
                orderLine.getPricePerTon());
        }

        log.info("Successfully tracked purchase order: {} for customer: {} with {} material requirements", 
                command.getPurchaseOrderNumber(), command.getCustomerName(), command.getOrderLines().size());
    }
} 