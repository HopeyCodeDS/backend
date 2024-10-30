package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WeighingBridge {
    private String bridgeNumber;
    private String scannedLicensePlate;
    private double truckGrossWeight;
    private LocalDateTime weighingTime;

    public WeighingBridge(String bridgeNumber) {
        this.bridgeNumber = bridgeNumber;
    }

    /// Method to scan the truck's license plate, register grossWeight, and assign a warehouse based on material type
    public void scanTruckAndRegisterWeight(String licensePlate, double grossWeight) {
        this.scannedLicensePlate = licensePlate;
        this.truckGrossWeight = grossWeight;
        this.weighingTime = LocalDateTime.now();
    }

}
