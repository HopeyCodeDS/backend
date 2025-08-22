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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageFeeCalculationUseCaseImpl implements StorageFeeCalculationUseCase {
    
    private final StorageTrackingRepositoryPort storageTrackingRepositoryPort;
    private final StorageFeeRepositoryPort storageFeeRepositoryPort;
    
    
    
    @Override
    @Transactional
    public void calculateDailyStorageFees(LocalDate calculationDate) {
        log.info("Calculating daily storage fees for date: {}", calculationDate);
        
        // Get all storage tracking records
        List<StorageTracking> allStorageRecords = storageTrackingRepositoryPort.findAll();
        
        log.info("Found {} storage tracking records", allStorageRecords.size());
        
        if (allStorageRecords.isEmpty()) {
            log.info("No storage tracking records found for date: {}", calculationDate);
            return;
        }

        // Log each record(i needed this for debugging purposes)
        for (StorageTracking record : allStorageRecords) {
            log.info("Record: PDT={}, Warehouse={}, Material={}, RemainingTons={}, StorageCost={}, CostInDollars={}", 
                record.getPdtId(), 
                record.getWarehouseNumber(), 
                record.getMaterialType(), 
                record.getRemainingTons(),
                record.getStorageCost(),
                record.getCostInDollars());
        }
        
        // Group records by warehouse and material
        Map<String, List<StorageTracking>> groupedRecords = allStorageRecords.stream()
            .collect(Collectors.groupingBy(record -> 
                record.getWarehouseNumber() + ":" + record.getMaterialType()
            ));
        
        log.info("Grouped into {} warehouse-material combinations", groupedRecords.size());
        
        // Calculate fees per warehouse-material combination
        for (Map.Entry<String, List<StorageTracking>> entry : groupedRecords.entrySet()) {
            String key = entry.getKey();
            List<StorageTracking> records = entry.getValue();
            
            String[] parts = key.split(":");
            String warehouseNumber = parts[0];
            String materialType = parts[1];
            
            // Check if fee already exists for this combination and date
            if (storageFeeRepositoryPort.existsByCalculationDateAndWarehouseAndMaterial(
                    calculationDate, warehouseNumber, materialType)) {
                log.info("Storage fee already calculated for {} on {} in warehouse {}", 
                    materialType, calculationDate, warehouseNumber);
                continue;
            }
            
            // Calculate total daily fee for this warehouse-material combination
            double totalDailyFee = calculateTotalDailyStorageFee(records, calculationDate);

            // Count unique PDTs (not total records)
            long uniquePdtCount = records.stream()
                .filter(StorageTracking::hasRemainingTons)
                .map(StorageTracking::getPdtId)
                .distinct()
                .count();
            
            int totalDeliveryBatches = (int) uniquePdtCount;
            
            if (totalDailyFee > 0) {
                // Creating storage fee record for this warehouse-material combination
                StorageFee storageFee = new StorageFee(
                    calculationDate,
                    warehouseNumber,
                    materialType,
                    records.get(0).getCustomerNumber(),
                    totalDailyFee,
                    totalDeliveryBatches
                );
                
                storageFeeRepositoryPort.save(storageFee);
                
                log.info("Storage fee saved: ${} for {} materials in warehouse {} on {}", 
                totalDailyFee, materialType, warehouseNumber, calculationDate);
            } else {
                log.warn("No storage fee calculated for {} in warehouse {} (totalDailyFee = {})", 
                    materialType, warehouseNumber, totalDailyFee);
            }
        }   
        
        log.info("Daily storage fee calculation completed for date: {}", calculationDate);
    }
    
    private double calculateTotalDailyStorageFee(List<StorageTracking> storageRecords, LocalDate calculationDate) {
        double totalFee = 0.0;
        
        for (StorageTracking record : storageRecords) {
            if (record.hasRemainingTons()) {
                // Using pre-calculated costInDollars from the record
                double costForDate = record.getCostInDollars();
                totalFee += costForDate;
                
                log.debug("PDT {}: {} tons remaining for {} days = ${} (Warehouse: {}, Material: {})", 
                    record.getPdtId(), 
                    record.getRemainingTons(), 
                    calculateDaysForDate(record, calculationDate),
                    costForDate,
                    record.getWarehouseNumber(),
                    record.getMaterialType()
                );
            }
        }
        
        log.info("Total fee calculated: ${} for {} records", totalFee, storageRecords.size());
        return totalFee;
    }

    // Helper method to calculate days for a specific date without modifying the record
    private long calculateDaysForDate(StorageTracking record, LocalDate calculationDate) {
        return java.time.temporal.ChronoUnit.DAYS.between(
            record.getDeliveryTime().toLocalDate(), 
            calculationDate
        );
    }
}