package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record TruckLeftWeighingBridge(
        @JsonProperty("movementId") UUID movementId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("bridgeNumber") String bridgeNumber,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("sellerId") UUID sellerId,
        @JsonProperty("truckWeight") double truckWeight) {
}