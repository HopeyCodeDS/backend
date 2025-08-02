package be.kdg.prog6.watersideContext.domain.commands;

import lombok.Getter;
import java.util.UUID;

@Getter
public class CompleteBunkeringCommand {
    private final UUID shippingOrderId;
    private final String bunkeringOfficerSignature;

    public CompleteBunkeringCommand(UUID shippingOrderId, String bunkeringOfficerSignature) {
        this.shippingOrderId = shippingOrderId;
        this.bunkeringOfficerSignature = bunkeringOfficerSignature;
    }
} 