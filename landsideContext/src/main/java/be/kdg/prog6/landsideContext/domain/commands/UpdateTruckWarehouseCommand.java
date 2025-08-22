package be.kdg.prog6.landsideContext.domain.commands;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import lombok.Getter;

@Getter
public class UpdateTruckWarehouseCommand {
    private final LicensePlate licensePlate;
    private final String warehouseNumber;

    public UpdateTruckWarehouseCommand(String licensePlate, String warehouseNumber) {
        this.licensePlate = new LicensePlate(licensePlate);
        this.warehouseNumber = warehouseNumber;
    }
} 