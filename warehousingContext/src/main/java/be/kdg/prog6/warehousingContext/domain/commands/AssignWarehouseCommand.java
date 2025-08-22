package be.kdg.prog6.warehousingContext.domain.commands;

import java.util.UUID;

public record AssignWarehouseCommand(
    UUID commandId,
    String licensePlate,
    String rawMaterialName,
    UUID sellerId,
    double truckWeight
) {
    public AssignWarehouseCommand(String licensePlate, String rawMaterialName, UUID sellerId, double truckWeight) {
        this(UUID.randomUUID(), licensePlate, rawMaterialName, sellerId, truckWeight);
    }
}