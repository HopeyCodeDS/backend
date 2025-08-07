package be.kdg.prog6.invoicingContext.domain;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class StorageTracking {
    private final UUID trackingId;
    private final String warehouseNumber;
    private final String customerNumber; // sellerId from PDT
    private final String materialType;
    private final double tonsStored;
    private final LocalDateTime deliveryTime;
    private final UUID pdtId;
    private double remainingTons; // For FIFO tracking when materials are loaded
    
    public StorageTracking(String warehouseNumber, String customerNumber, String materialType,
                          double tonsStored, LocalDateTime deliveryTime, UUID pdtId) {
        this.trackingId = UUID.randomUUID();
        this.warehouseNumber = warehouseNumber;
        this.customerNumber = customerNumber;
        this.materialType = materialType;
        this.tonsStored = tonsStored;
        this.remainingTons = tonsStored; // Initially all tons are remaining
        this.deliveryTime = deliveryTime;
        this.pdtId = pdtId;
    }
    
    // Method to deduct tons when materials are loaded (FIFO)
    public void deductTons(double tonsToDeduct) {
        if (tonsToDeduct > this.remainingTons) {
            throw new IllegalStateException("Cannot deduct more tons than remaining");
        }
        this.remainingTons -= tonsToDeduct;
    }
    
    public boolean hasRemainingTons() {
        return this.remainingTons > 0;
    }
} 