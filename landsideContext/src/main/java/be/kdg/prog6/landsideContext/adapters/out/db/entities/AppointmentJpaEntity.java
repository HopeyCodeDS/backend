package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(catalog = "landside", name = "appointments")
public class AppointmentJpaEntity {

    // Getters and setters
    @Id
    @Column(name="appointment_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private Long appointmentId;

//    @ManyToOne(cascade = CascadeType.ALL)
    @Setter
    @ManyToOne
    @JoinColumn(name = "license_plate", referencedColumnName = "license_plate")
    private TruckJpaEntity truck;

    @Setter
    @Column(name = "arrival_window", nullable = false)
    private LocalDateTime arrivalWindow;

    @Setter
    @Embedded
    @AttributeOverride(name = "uuid", column = @Column(name = "seller_id", columnDefinition = "CHAR(36)"))
    private SellerID sellerId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "material_type", nullable = false)
    private MaterialType materialType;

    @Setter
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private SlotJpaEntity slot;

}
