package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import be.kdg.prog6.warehousingContext.domain.commands.TrackPurchaseOrderFulfillmentCommand;
import be.kdg.prog6.warehousingContext.ports.in.TrackPurchaseOrderFulfillmentUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PurchaseOrderFulfillmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackPurchaseOrderFulfillmentUseCaseImpl implements TrackPurchaseOrderFulfillmentUseCase {

    private final PurchaseOrderFulfillmentRepositoryPort repositoryPort;

    @Override
    public void trackNewPurchaseOrder(TrackPurchaseOrderFulfillmentCommand command) {
        log.info("Tracking new purchase order for fulfillment: {}", command.getPurchaseOrderNumber());
        
        PurchaseOrderFulfillmentTracking fulfillment = new PurchaseOrderFulfillmentTracking(
            command.getPurchaseOrderNumber(),
            command.getCustomerNumber(),
            command.getCustomerName(),
            command.getTotalValue(),
            command.getOrderDate()
        );
        
        repositoryPort.save(fulfillment);
        
        log.info("Successfully tracked purchase order: {} for customer: {}", 
                command.getPurchaseOrderNumber(), command.getCustomerName());
    }
} 