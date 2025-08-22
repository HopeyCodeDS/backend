package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.RawMaterial;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import lombok.Getter;

@Getter
public class RecordWeighingCommand {
    private final LicensePlate licensePlate;
    private final WeighingBridge weighingBridge;
    private final double grossWeight;
    private final double tareWeight;
    private final RawMaterial rawMaterial;

    public RecordWeighingCommand(LicensePlate licensePlate, 
                                WeighingBridge weighingBridge,
                                double grossWeight, 
                                double tareWeight,
                                RawMaterial rawMaterial) {
        this.licensePlate = licensePlate;
        this.weighingBridge = weighingBridge;
        this.grossWeight = grossWeight;
        this.tareWeight = tareWeight;
        this.rawMaterial = rawMaterial;
    }
} 