package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentCreatedEvent {
    private String licensePlate;
    private UUID sellerId;
    private String materialType;
    private LocalDateTime arrivalWindow;

    public AppointmentCreatedEvent(String licensePlate, UUID sellerId, String materialType, LocalDateTime arrivalWindow) {
        this.licensePlate = licensePlate;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.arrivalWindow = arrivalWindow;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getSellerId() {
        return sellerId.toString();
    }

    public String getMaterialType() {
        return materialType;
    }

    public LocalDateTime getArrivalWindow() {
        return arrivalWindow;
    }

    @Override
    public String toString() {
        return "AppointmentCreatedEvent{" +
                "licensePlate='" + licensePlate + '\'' +
                ", sellerId=" + sellerId +
                ", materialType='" + materialType + '\'' +
                ", arrivalWindow=" + arrivalWindow +
                '}';
    }
}