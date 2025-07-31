package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record PDTGenerated(
        @JsonProperty("pdtId") UUID pdtId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("warehouseNumber") String warehouseNumber,
        @JsonProperty("conveyorBeltNumber") String conveyorBeltNumber,
        @JsonProperty("payloadWeight") double payloadWeight,
        @JsonProperty("sellerId") String sellerId,
        @JsonProperty("deliveryTime") LocalDateTime deliveryTime,
        @JsonProperty("newWeighingBridgeNumber") String newWeighingBridgeNumber,
        @JsonProperty("warehouseActivityId") UUID warehouseActivityId) {
}