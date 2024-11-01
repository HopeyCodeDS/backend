package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(catalog = "landside", name = "appointments")
public class AppointmentJpaEntity {

    // Getters and setters
    @Id
    @Column(name = "appointment_id", columnDefinition = "varchar(36)")
    private String appointmentId = UUID.randomUUID().toString();  // Automatically generate UUID


    //    @ManyToOne(cascade = CascadeType.ALL)
    @Setter
    @ManyToOne
    @JoinColumn(name = "license_plate", referencedColumnName = "license_plate")
    private TruckJpaEntity truck;

    @Setter
    @Column(name = "arrival_window", nullable = false)
    private LocalDateTime arrivalWindow;

    @Setter
    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "slot_id")
    private SlotJpaEntity slot;

}
