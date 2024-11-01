package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(catalog = "landside", name = "slots")
@Getter
@Setter
public class SlotJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // This enables auto-increment
    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
    private List<AppointmentJpaEntity> appointments;

    public SlotJpaEntity() {
    }
}
