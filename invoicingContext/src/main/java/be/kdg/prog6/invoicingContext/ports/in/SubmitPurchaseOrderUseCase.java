package be.kdg.prog6.invoicingContext.ports.in;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.commands.SubmitPurchaseOrderCommand;

public interface SubmitPurchaseOrderUseCase {
    PurchaseOrder submitPurchaseOrder(SubmitPurchaseOrderCommand command);
} 