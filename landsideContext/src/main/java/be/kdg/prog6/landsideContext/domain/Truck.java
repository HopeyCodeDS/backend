package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Truck {
    private final UUID truckId;
    private final LicensePlate licensePlate;
    private final TruckType truckType;
    
    public enum TruckType {
        SMALL(0.25),    // 250kg capacity
        MEDIUM(10.0),   // 10 ton capacity
        LARGE(25.0);    // 25 ton capacity (most popular)
        
        private final double capacityInTons;
        
        TruckType(double capacityInTons) {
            this.capacityInTons = capacityInTons;
        }
        
        public double getCapacityInTons() {
            return capacityInTons;
        }
    }
    
    public Truck(UUID truckId, LicensePlate licensePlate, TruckType truckType) {
        this.truckId = truckId;
        this.licensePlate = licensePlate;
        this.truckType = truckType;
    }
    
    public double getMaxPayloadCapacity() {
        return truckType.getCapacityInTons();
    }
} 