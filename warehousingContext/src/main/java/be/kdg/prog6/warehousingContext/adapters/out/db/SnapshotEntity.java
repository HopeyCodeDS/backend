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
@Table(catalog = "warehousing", name = "snapshots")
public class SnapshotEntity {
    
    @Id
    @Column(name = "snapshot_id")
    private UUID snapshotId;
    
    @Column(name = "aggregate_id", nullable = false)
    private UUID aggregateId;
    
    @Column(name = "snapshot_data", nullable = false, columnDefinition = "TEXT")
    private String snapshotData;
    
    @Column(name = "version", nullable = false)
    private long version;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
