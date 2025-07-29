package be.kdg.prog6.landsideContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record TruckEnteredGate(
        @JsonProperty("movementId") UUID movementId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("entryTime") LocalDateTime entryTime) {
} 