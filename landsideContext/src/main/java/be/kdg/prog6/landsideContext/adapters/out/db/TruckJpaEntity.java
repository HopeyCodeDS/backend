package be.kdg.prog6.landsideContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "landside", name = "trucks")
public class TruckJpaEntity {
    
    @Id
    @Column(name = "truck_id")
    private UUID truckId;
    
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "truck_type", nullable = false)
    private TruckType truckType;
    
    @Column(name = "capacity_in_tons", nullable = false)
    private Double capacityInTons;
    
    public enum TruckType {
        SMALL, MEDIUM, LARGE
    }
}
 