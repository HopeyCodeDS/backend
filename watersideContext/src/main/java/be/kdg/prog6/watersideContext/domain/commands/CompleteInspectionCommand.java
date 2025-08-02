package be.kdg.prog6.watersideContext.domain.commands;

import lombok.Getter;
import java.util.UUID;

@Getter
public class CompleteInspectionCommand {
    private final UUID shippingOrderId;
    private final String inspectorSignature;

    public CompleteInspectionCommand(UUID shippingOrderId, String inspectorSignature) {
        this.shippingOrderId = shippingOrderId;
        this.inspectorSignature = inspectorSignature;
    }
} 