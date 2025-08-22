package be.kdg.prog6.landsideContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record TruckArrivedEvent(
        @JsonProperty("appointmentId") UUID appointmentId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("arrivalTime") LocalDateTime arrivalTime) {
}
