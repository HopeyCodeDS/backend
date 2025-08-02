package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteInspectionCommand;

public interface CompleteInspectionUseCase {
    ShippingOrder completeInspection(CompleteInspectionCommand command);
} 