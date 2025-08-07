package be.kdg.prog6.invoicingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommissionFee {
    private final UUID commissionFeeId;
    private final String purchaseOrderNumber;
    private final String customerNumber;
    private final String sellerId;
    private final double amount;
    private final LocalDateTime calculationDate;
    
    public CommissionFee(String purchaseOrderNumber, String customerNumber, 
                        String sellerId, double amount, LocalDateTime calculationDate) {
        this.commissionFeeId = UUID.randomUUID();
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.customerNumber = customerNumber;
        this.sellerId = sellerId;
        this.amount = amount;
        this.calculationDate = calculationDate;
    }

    public static double calculateCommissionAmount(double totalValue) { // 1% commission
        return Math.round(totalValue * 0.01 * 100.0) / 100.0;
    }

    
} 