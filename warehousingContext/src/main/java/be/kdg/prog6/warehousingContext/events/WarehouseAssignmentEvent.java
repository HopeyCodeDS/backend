package be.kdg.prog6.warehousingContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseAssignmentEvent(
        @JsonProperty("assignmentId") UUID assignmentId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("warehouseNumber") String warehouseNumber,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("sellerId") String sellerId,
        @JsonProperty("assignmentTime") LocalDateTime assignmentTime) {
}