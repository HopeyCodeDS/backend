package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(catalog = "landside", name = "appointments")
public class AppointmentJpaEntity {
    
    @Id
    @Column(name = "appointment_id")
    private UUID appointmentId;
    
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "arrival_window_start", nullable = false)
    private LocalDateTime arrivalWindowStart;
    
    @Column(name = "arrival_window_end", nullable = false)
    private LocalDateTime arrivalWindowEnd;
    
    @Column(name = "raw_material_name", nullable = false)
    private String rawMaterialName;
    
    @Column(name = "raw_material_price_per_ton", nullable = false)
    private Double rawMaterialPricePerTon;
    
    @Column(name = "raw_material_storage_price_per_ton_per_day", nullable = false)
    private Double rawMaterialStoragePricePerTonPerDay;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;
    
    @Column(name = "actual_arrival_time")
    private LocalDateTime actualArrivalTime;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private TruckJpaEntity truck;
}
