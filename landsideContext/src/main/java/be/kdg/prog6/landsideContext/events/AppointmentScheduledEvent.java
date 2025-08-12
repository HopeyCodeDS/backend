package be.kdg.prog6.landsideContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentScheduledEvent(
        @JsonProperty("appointmentId") UUID appointmentId,
        @JsonProperty("sellerId") String sellerId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("arrivalWindowStart") LocalDateTime arrivalWindowStart,
        @JsonProperty("arrivalWindowEnd") LocalDateTime arrivalWindowEnd,
        @JsonProperty("scheduledTime") LocalDateTime scheduledTime) {
}