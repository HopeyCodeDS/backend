package be.kdg.prog6.landsideContext.ports.out;

import java.util.UUID;

public interface WarehouseAssignmentReceivedPort {
    void listenForWarehouseAssignment(UUID movementId);
    void handleWarehouseAssignment(String licensePlate, String warehouseNumber);
} 