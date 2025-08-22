package be.kdg.prog6.invoicingContext.domain.commands;

import lombok.Getter;
import java.util.UUID;

@Getter
public class UpdatePurchaseOrderStatusCommand {
    private final UUID purchaseOrderId;
    private final String status;

    public UpdatePurchaseOrderStatusCommand(UUID purchaseOrderId, String status) {
        this.purchaseOrderId = purchaseOrderId;
        this.status = status;
    }
}
