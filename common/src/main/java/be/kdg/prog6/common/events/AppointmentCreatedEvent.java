package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentCreatedEvent {
    private String appointmentId;
    private UUID sellerId;
    private String materialType;
    private LocalDateTime arrivalWindow;

    public AppointmentCreatedEvent(String appointmentId, UUID sellerId, String materialType, LocalDateTime arrivalWindow) {
        this.appointmentId = appointmentId;
        this.sellerId = sellerId;
        this.materialType = materialType;
        this.arrivalWindow = arrivalWindow;
    }

    public String getAppointmentId() {
        return appointmentId;
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
}