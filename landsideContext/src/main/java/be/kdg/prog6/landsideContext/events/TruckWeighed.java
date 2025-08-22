package be.kdg.prog6.landsideContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record TruckWeighed(
        @JsonProperty("movementId") UUID movementId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("sellerId") String sellerId,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("truckWeight") double truckWeight,
        @JsonProperty("weighingTime") LocalDateTime weighingTime) {
}
