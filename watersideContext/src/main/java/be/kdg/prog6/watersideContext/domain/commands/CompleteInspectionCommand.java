package be.kdg.prog6.watersideContext.domain.commands;

import java.util.UUID;

public record CompleteInspectionCommand(UUID shippingOrderId, String inspectorSignature) {
}