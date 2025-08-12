package be.kdg.prog6.invoicingContext.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Getter
public class StorageTracking {
    private final UUID trackingId;
    private final String warehouseNumber;
    private final String customerNumber;
    private final String materialType;
    private final double tonsStored;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private final LocalDateTime deliveryTime;
    private final UUID pdtId;
    private double remainingTons;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDate storageCostCalculationDate;
    private long numberOfDays;
    private double costInDollars;
    private double storageCost;
    private static final LocalDate DEFAULT_CALCULATION_DATE = LocalDate.of(2025, 9, 3);
    
    // Storage rates per ton per day for each material type
    private static final Map<String, Double> STORAGE_RATES = Map.of(
        "GYPSUM", 1.0,      // $1 per ton per day
        "IRON_ORE", 5.0,    // $5 per ton per day
        "CEMENT", 3.0,      // $3 per ton per day
        "PETCOKE", 10.0,    // $10 per ton per day
        "SLAG", 7.0         // $7 per ton per day
    );
    
    public StorageTracking(String warehouseNumber, String customerNumber, String materialType,
                          double tonsStored, LocalDateTime deliveryTime, UUID pdtId) {
        this.trackingId = UUID.randomUUID();
        this.warehouseNumber = warehouseNumber;
        this.customerNumber = customerNumber;
        this.materialType = materialType;
        this.tonsStored = tonsStored;
        this.remainingTons = tonsStored;
        this.deliveryTime = deliveryTime;
        this.pdtId = pdtId;
        this.storageCost = STORAGE_RATES.getOrDefault(materialType.toUpperCase(), 0.0);
        
        // Calculate storage costs from delivery date to current date
        calculateInitialStorageCost(DEFAULT_CALCULATION_DATE);
    }

    // Constructor for loading existing records
    public StorageTracking(UUID trackingId, String warehouseNumber, String customerNumber, String materialType,
                          double tonsStored, LocalDateTime deliveryTime, UUID pdtId, double remainingTons,
                          LocalDate storageCostCalculationDate, long numberOfDays, double costInDollars, double storageCost) {
        this.trackingId = trackingId;
        this.warehouseNumber = warehouseNumber;
        this.customerNumber = customerNumber;
        this.materialType = materialType;
        this.tonsStored = tonsStored;
        this.remainingTons = remainingTons;
        this.deliveryTime = deliveryTime;
        this.pdtId = pdtId;
        this.storageCostCalculationDate = storageCostCalculationDate;
        this.numberOfDays = numberOfDays;
        this.costInDollars = costInDollars;
        this.storageCost = storageCost;
    }
    
    public void deductTons(double tonsToDeduct) {
        if (tonsToDeduct > this.remainingTons) {
            throw new IllegalStateException("Cannot deduct more tons than remaining");
        }
        this.remainingTons -= tonsToDeduct;
        
        // Recalculate storage costs after deduction
        calculateInitialStorageCost(DEFAULT_CALCULATION_DATE);
    }
    
    public boolean hasRemainingTons() {
        return this.remainingTons > 0;
    }
    
    public void setRemainingTons(double remainingTons) {
        this.remainingTons = remainingTons;
        // Recalculate storage costs after setting remaining tons
        calculateInitialStorageCost(DEFAULT_CALCULATION_DATE);
    }
    
    // Calculate storage costs for a specific date
    private void calculateInitialStorageCost(LocalDate calculationDate) {
        this.storageCostCalculationDate = calculationDate;
        
        // Calculate days from delivery to the specified calculation date
        this.numberOfDays = ChronoUnit.DAYS.between(
            this.deliveryTime.toLocalDate(), 
            calculationDate
        );
        
        // Only calculate cost if delivery is in the past or present relative to calculation date
        if (this.numberOfDays >= 0 && this.remainingTons > 0) {
            this.costInDollars = this.remainingTons * this.storageCost * this.numberOfDays;
        } else {
            this.costInDollars = 0.0;
        }
    }
    
    // Method to recalculate costs for a specific date (for daily calculations)
    public void recalculateStorageCostForDate(LocalDate calculationDate) {
        this.storageCostCalculationDate = calculationDate;
        this.numberOfDays = ChronoUnit.DAYS.between(
            this.deliveryTime.toLocalDate(), 
            calculationDate
        );
        
        if (this.numberOfDays >= 0 && this.remainingTons > 0) {
            this.costInDollars = this.remainingTons * this.storageCost * this.numberOfDays;
        } else {
            this.costInDollars = 0.0;
        }
    }
    
    // Getters for storage cost fields
    public LocalDate getStorageCostCalculationDate() { return storageCostCalculationDate; }
    public long getNumberOfDays() { return numberOfDays; }
    public double getCostInDollars() { return costInDollars; }
    public double getStorageCost() { return storageCost; }
}   