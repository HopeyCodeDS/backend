package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.MaterialType;

import java.util.UUID;

public class DockingEvent {
    private UUID appointmentId;
    private String licensePlate;
    private MaterialType materialType;
    private String weighingBridgeId;

    public DockingEvent(UUID appointmentId, String licensePlate, MaterialType materialType, String weighingBridgeId) {
        this.appointmentId = appointmentId;
        this.licensePlate = licensePlate;
        this.materialType = materialType;
        this.weighingBridgeId = weighingBridgeId;
    }

    // Getters and Setters
    public UUID getAppointmentId() {
        return appointmentId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
    public String getWeighingBridgeId() {  // New getter for weighingBridgeId
        return weighingBridgeId;
    }

    public void setWeighingBridgeId(String weighingBridgeId) {
        this.weighingBridgeId = weighingBridgeId;
    }
}