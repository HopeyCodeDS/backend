package be.kdg.prog6.invoicingContext.ports.in;

import be.kdg.prog6.invoicingContext.domain.PurchaseOrder;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderCommand;
import be.kdg.prog6.invoicingContext.domain.commands.UpdatePurchaseOrderStatusCommand;

public interface UpdatePurchaseOrderUseCase {
    PurchaseOrder updatePurchaseOrder(UpdatePurchaseOrderCommand command);
    PurchaseOrder updatePurchaseOrderStatus(UpdatePurchaseOrderStatusCommand command);
}
