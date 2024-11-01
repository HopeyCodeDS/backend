package be.kdg.prog6.common.commands;

import be.kdg.prog6.common.domain.MaterialType;

import java.util.UUID;

public class DockingCommand {
    private final UUID appointmentId;
    private final String licensePlate;
    private final MaterialType materialType;
    private final String weighingBridgeId;

    public DockingCommand(UUID appointmentId, String licensePlate, MaterialType materialType, String weighingBridgeId) {
        // Optional: Add validation logic
        if (appointmentId == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        if (licensePlate == null || licensePlate.isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null");
        }
        if (weighingBridgeId == null || weighingBridgeId.isEmpty()) {
            throw new IllegalArgumentException("Weighing bridge ID cannot be null or empty");
        }

        this.appointmentId = appointmentId;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.weighingBridgeId = weighingBridgeId;
    }

    // Getters for the command properties
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getWeighingBridgeId() {
        return weighingBridgeId;
    }
}
