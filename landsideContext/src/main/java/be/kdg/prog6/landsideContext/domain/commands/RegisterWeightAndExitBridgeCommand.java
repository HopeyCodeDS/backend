package be.kdg.prog6.landsideContext.domain.commands;

import lombok.Value;

@Value
public class RegisterWeightAndExitBridgeCommand {
    String licensePlate;
    Double weight;
    String rawMaterialName;
} 