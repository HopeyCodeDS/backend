package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;

public interface AssignWarehouseUseCase {
    String assignWarehouse(AssignWarehouseCommand command);
} 