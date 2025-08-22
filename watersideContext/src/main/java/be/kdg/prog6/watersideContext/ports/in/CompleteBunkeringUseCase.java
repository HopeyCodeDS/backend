package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.CompleteBunkeringCommand;

public interface CompleteBunkeringUseCase {
    ShippingOrder completeBunkering(CompleteBunkeringCommand command);
} 