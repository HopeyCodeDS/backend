package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;

public interface WeighTruckAndAssignWarehouseUseCase {
    String weighAndAssignWarehouseToTruck(String licensePlate, double weight); // Returns warehouse number after weighing
    WeighBridgeTicket generateWeighBridgeTicket(String licensePlate, double grossWeight, double tareWeight);
}
