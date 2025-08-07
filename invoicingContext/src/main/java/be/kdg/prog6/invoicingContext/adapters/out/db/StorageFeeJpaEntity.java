package be.kdg.prog6.invoicingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "invoicing", name = "storage_fees")
@Getter
@Setter
public class StorageFeeJpaEntity {
    
    @Id
    @Column(name = "storage_fee_id")
    private UUID storageFeeId;
    
    @Column(name = "warehouse_number", nullable = false)
    private String warehouseNumber;
    
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;
    
    @Column(name = "material_type", nullable = false)
    private String materialType;
    
    @Column(name = "total_tons_stored", nullable = false)
    private double totalTonsStored;
    
    @Column(name = "fee_amount", nullable = false)
    private double feeAmount;
    
    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "number_of_pdts", nullable = false)
    private int numberOfPDTs;
} 