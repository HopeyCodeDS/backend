package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record WarehouseAssigned(
        @JsonProperty("assignmentId") UUID assignmentId,
        @JsonProperty("licensePlate") String licensePlate,
        @JsonProperty("warehouseNumber") String warehouseNumber,
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("sellerId") String sellerId,
        @JsonProperty("truckWeight") double truckWeight) {
}