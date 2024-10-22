package be.kdg.prog6.landsideContext.adapters.out.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "slots")
@Getter
@Setter
public class SlotJpaEntity {
    @Id
    @Column(name = "slot_id", nullable = false, length = 255)
    @JdbcTypeCode(Types.VARCHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String slotId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
    private List<AppointmentJpaEntity> appointments;

    public SlotJpaEntity() {
    }
}
