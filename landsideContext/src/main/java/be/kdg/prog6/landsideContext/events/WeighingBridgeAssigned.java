package be.kdg.prog6.landsideContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public record WeighingBridgeAssigned(
        @JsonProperty("movementId") UUID movementId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("bridgeNumber") String bridgeNumber,
        @JsonProperty("assignmentTime") LocalDateTime assignmentTime) {
}
 