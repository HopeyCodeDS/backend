package be.kdg.prog6.landsideContext.domain;

import be.kdg.prog6.common.domain.MaterialType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private Truck truck;
    private LocalDateTime arrivalWindow;
    private UUID sellerId;
    private MaterialType materialType;
    private Slot slot;

    public Appointment(Truck truck, LocalDateTime arrivalWindow, UUID sellerId, MaterialType materialType, Slot slot) {
        this.truck = truck;
        this.arrivalWindow = arrivalWindow;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.slot = slot;
    }

    public Truck getTruck() {
        return truck;
    }

    public LocalDateTime getArrivalWindow() {
        return arrivalWindow;
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public Slot getSlot() {
        return slot;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }
}