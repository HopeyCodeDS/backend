package be.kdg.prog6.watersideContext.domain.commands;

import java.util.UUID;

public record CompleteBunkeringCommand(UUID shippingOrderId, String bunkeringOfficerSignature) {
}