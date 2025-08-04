package be.kdg.prog6.warehousingContext.domain;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OldestStockAllocationStrategy {
    
    /**
     * Pure domain logic for allocating oldest stock (FIFO principle)
     * This ensures sellers are charged as little as possible for storage
     */
    public static List<PayloadDeliveryTicket> allocateOldestStockFIFO(
            List<PayloadDeliveryTicket> availableStock, double requiredAmount) {
        
        log.info("Applying FIFO allocation strategy for {} tons from {} available PDTs", 
            requiredAmount, availableStock.size());
        
        List<PayloadDeliveryTicket> allocatedStock = new ArrayList<>();
        double allocatedAmount = 0.0;
        
        for (PayloadDeliveryTicket pdt : availableStock) {  // Oldest first
            if (allocatedAmount >= requiredAmount) {
                break;
            }
            allocatedStock.add(pdt); // Add the oldest PDT first to allocated stock
            allocatedAmount += pdt.getPayloadWeight();
        }
        
        return allocatedStock;
    }
    
    public static double calculateTotalAvailableStock(List<PayloadDeliveryTicket> availableStock) {
        return availableStock.stream()
            .mapToDouble(PayloadDeliveryTicket::getPayloadWeight)
            .sum();
    }
} 