package be.kdg.prog6.landsideContext.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record WeighingRecord(
        UUID weighingId,
        LicensePlate licensePlate,
        WeighingBridge weighingBridge,
        double grossWeight, // Truck + payload weight
        double tareWeight,  // Empty truck weight
        double payloadWeight, // Calculated: gross - tare
        LocalDateTime weighingTime,
        RawMaterial rawMaterial
) {
    public WeighingRecord(LicensePlate licensePlate,
                         WeighingBridge weighingBridge,
                         double grossWeight,
                         double tareWeight,
                         RawMaterial rawMaterial) {
        this(
            UUID.randomUUID(),
            licensePlate,
            weighingBridge,
            grossWeight,
            tareWeight,
            grossWeight - tareWeight,
            LocalDateTime.now(),
            rawMaterial
        );
    }

    public boolean isValidPayload() {
        return payloadWeight > 0 && payloadWeight <= 25.0; // Max 25 tons
    }
}