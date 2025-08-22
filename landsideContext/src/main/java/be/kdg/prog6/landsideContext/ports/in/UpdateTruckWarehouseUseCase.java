package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckWarehouseCommand;
 
public interface UpdateTruckWarehouseUseCase {
    void updateTruckWarehouse(UpdateTruckWarehouseCommand command);
} 