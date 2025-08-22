package be.kdg.prog6.landsideContext.adapters.out.db;

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
@Table(catalog = "landside", name = "weighbridge_tickets")
public class WeighbridgeTicketJpaEntity {
    @Id
    @Column(name = "ticket_id")
    private UUID ticketId;
    
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    
    @Column(name = "gross_weight", nullable = false)
    private double grossWeight;
    
    @Column(name = "tare_weight", nullable = false)
    private double tareWeight;
    
    @Column(name = "net_weight", nullable = false)
    private double netWeight;
    
    @Column(name = "weighing_time", nullable = false)
    private LocalDateTime weighingTime;
} 