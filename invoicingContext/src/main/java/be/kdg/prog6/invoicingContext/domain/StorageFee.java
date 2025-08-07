package be.kdg.prog6.invoicingContext.domain;

import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class StorageFee {
    private final UUID storageFeeId;
    private final String warehouseNumber;
    private final String customerNumber;
    private final String materialType;
    private final double totalTonsStored;
    private final double feeAmount;
    private final LocalDate calculationDate;
    private final LocalDateTime createdAt;
    private final int numberOfPDTs; // Number of PDTs contributing to this fee
    
    public StorageFee(String warehouseNumber, String customerNumber, String materialType,
                     double totalTonsStored, double feeAmount, LocalDate calculationDate, 
                     LocalDateTime createdAt, int numberOfPDTs) {
        this.storageFeeId = UUID.randomUUID();
        this.warehouseNumber = warehouseNumber;
        this.customerNumber = customerNumber;
        this.materialType = materialType;
        this.totalTonsStored = totalTonsStored;
        this.feeAmount = feeAmount;
        this.calculationDate = calculationDate;
        this.createdAt = createdAt;
        this.numberOfPDTs = numberOfPDTs;
    }
} 