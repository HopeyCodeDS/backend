package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.common.events.WarehouseAssigned;

public interface WarehouseAssignedPort {
    void warehouseAssigned(WarehouseAssigned assignment);
} 