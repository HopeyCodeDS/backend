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
@Table(catalog = "landside", name = "weighing_bridges")
public class WeighingBridgeJpaEntity {
    
    @Id
    @Column(name = "bridge_id")
    private UUID bridgeId;
    
    @Column(name = "bridge_number", nullable = false, unique = true)
    private String bridgeNumber;
    
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;
}
