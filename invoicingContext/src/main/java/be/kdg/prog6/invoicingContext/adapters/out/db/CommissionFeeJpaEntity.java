package be.kdg.prog6.invoicingContext.adapters.out.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "invoicing", name = "commission_fees")
@Getter
@Setter
public class CommissionFeeJpaEntity {
    
    @Id
    @Column(name = "commission_fee_id")
    private UUID commissionFeeId;
    
    @Column(name = "purchase_order_number", nullable = false)
    private String purchaseOrderNumber;
    
    @Column(name = "customer_number", nullable = false)
    private String customerNumber;
    
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "amount", nullable = false)
    private double amount;
    
    @Column(name = "calculation_date", nullable = false)
    private LocalDateTime calculationDate;
} 