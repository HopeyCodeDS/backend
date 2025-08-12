package be.kdg.prog6.invoicingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(catalog = "invoicing", name = "storage_fees")
@Getter
@Setter
public class StorageFeeJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "storage_fee_id", unique = true, nullable = false)
    private UUID storageFeeId;
    
    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "material_type", nullable = false)
    private String materialType;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "total_daily_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDailyFee;
    
    @Column(name = "total_delivery_batches", nullable = false)
    private Integer totalDeliveryBatches;
} 