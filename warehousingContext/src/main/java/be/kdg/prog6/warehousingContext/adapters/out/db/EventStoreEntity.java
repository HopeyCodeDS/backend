package be.kdg.prog6.warehousingContext.adapters.out.db;

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
@Table(catalog = "warehousing", name = "event_stores")
public class EventStoreEntity {
    
    @Id
    @Column(name = "event_id")
    private UUID eventId;
    
    @Column(name = "aggregate_id", nullable = false)
    private UUID aggregateId;
    
    @Column(name = "event_type", nullable = false)
    private String eventType;
    
    @Column(name = "event_data", nullable = false, columnDefinition = "TEXT")
    private String eventData;
    
    @Column(name = "version", nullable = false)
    private long version;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
