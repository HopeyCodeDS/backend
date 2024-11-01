package be.kdg.prog6.landsideContext.adapters.in.dto;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;

import java.io.Serializable;

public class TruckDTO {
    private final LicensePlate licensePlate;
    private final double weight;

    public TruckDTO(LicensePlate licensePlate, double weight) {
        this.licensePlate = licensePlate;
        this.weight = weight;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public double getWeight() {
        return weight;
    }
}


//(LicensePlate licensePlate, double weight)