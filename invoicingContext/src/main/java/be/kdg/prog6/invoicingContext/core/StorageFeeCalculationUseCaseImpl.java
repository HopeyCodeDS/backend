package be.kdg.prog6.invoicingContext.core;

import be.kdg.prog6.invoicingContext.domain.StorageFee;
import be.kdg.prog6.invoicingContext.domain.StorageTracking;
import be.kdg.prog6.invoicingContext.ports.in.StorageFeeCalculationUseCase;
import be.kdg.prog6.invoicingContext.ports.out.StorageFeeRepositoryPort;
import be.kdg.prog6.invoicingContext.ports.out.StorageTrackingRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageFeeCalculationUseCaseImpl implements StorageFeeCalculationUseCase {
    
    private final StorageTrackingRepositoryPort storageTrackingRepositoryPort;
    private final StorageFeeRepositoryPort storageFeeRepositoryPort;
    
    // Storage rates per ton per day for each material type
    private static final Map<String, Double> STORAGE_RATES = Map.of(
        "GYPSUM", 1.0,      // $1 per ton per day
        "IRON_ORE", 5.0,    // $5 per ton per day
        "CEMENT", 3.0,      // $3 per ton per day
        "PETCOKE", 10.0,    // $10 per ton per day
        "SLAG", 7.0         // $7 per ton per day
    );
    
    @Override
    @Transactional
    public void calculateDailyStorageFees(LocalDate calculationDate) {
        log.info("Calculating daily storage fees for date: {}", calculationDate);
        
        // Get all storage tracking records grouped by warehouse and material
        Map<String, List<StorageTracking>> warehouseStorage = 
            storageTrackingRepositoryPort.findAllGroupedByWarehouseAndMaterial();
        
        for (Map.Entry<String, List<StorageTracking>> entry : warehouseStorage.entrySet()) {
            String warehouseKey = entry.getKey(); // "warehouseNumber:materialType"
            List<StorageTracking> storageRecords = entry.getValue();
            
            if (!storageRecords.isEmpty()) {
                StorageTracking firstRecord = storageRecords.get(0);
                String warehouseNumber = firstRecord.getWarehouseNumber();
                String materialType = firstRecord.getMaterialType();
                String customerNumber = firstRecord.getCustomerNumber();
                
                // Calculate storage fee using FIFO logic (as per assignment example)
                double totalStorageFee = calculateFIFOStorageFee(storageRecords, calculationDate);
                double totalTonsStored = storageRecords.stream()
                    .mapToDouble(StorageTracking::getRemainingTons)
                    .sum();
                
                if (totalStorageFee > 0) {
                    // Create storage fee record
                    StorageFee fee = new StorageFee(
                        warehouseNumber,
                        customerNumber,
                        materialType,
                        totalTonsStored,
                        totalStorageFee,
                        calculationDate,
                        LocalDateTime.now(),
                        storageRecords.size()
                    );
                    
                    storageFeeRepositoryPort.save(fee);
                    
                    log.info("Storage fee calculated: ${} for warehouse {} ({} tons of {}, {} PDTs)", 
                        totalStorageFee, warehouseNumber, totalTonsStored, materialType, storageRecords.size());
                }
            }
        }
        
        log.info("Daily storage fee calculation completed for date: {}", calculationDate);
    }
    
    private double calculateFIFOStorageFee(List<StorageTracking> storageRecords, LocalDate calculationDate) {
        // Sort by delivery time (FIFO - oldest first)
        storageRecords.sort(Comparator.comparing(StorageTracking::getDeliveryTime));
        
        double totalFee = 0.0;
        
        for (StorageTracking record : storageRecords) {
            if (record.hasRemainingTons()) {
                // Calculate days since delivery
                long daysStored = ChronoUnit.DAYS.between(
                    record.getDeliveryTime().toLocalDate(), 
                    calculationDate
                );
                
                // Get storage rate for material type
                double ratePerTonPerDay = STORAGE_RATES.getOrDefault(record.getMaterialType().toUpperCase(), 0.0);
                
                // Calculate fee for remaining tons
                double fee = record.getRemainingTons() * ratePerTonPerDay * daysStored;
                totalFee += fee;
                
                log.debug("PDT {}: {} tons remaining for {} days at ${}/ton/day = ${}", 
                    record.getPdtId(), record.getRemainingTons(), daysStored, ratePerTonPerDay, fee);
            }
        }
        
        return totalFee;
    }
} 