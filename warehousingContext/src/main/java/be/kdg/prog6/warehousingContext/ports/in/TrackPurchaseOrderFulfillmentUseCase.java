package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.commands.TrackPurchaseOrderFulfillmentCommand;

public interface TrackPurchaseOrderFulfillmentUseCase {
    void trackNewPurchaseOrder(TrackPurchaseOrderFulfillmentCommand command);
} 