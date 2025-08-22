package be.kdg.prog6.warehousingContext.domain;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

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
        double remainingRequired = requiredAmount;
        
        for (PayloadDeliveryTicket pdt : availableStock) {  // Oldest first
            if (remainingRequired <= 0) {
                break;
            }

            double pdtWeight = pdt.getPayloadWeight();
            double amountToDeduct = Math.min(remainingRequired, pdtWeight);
            
            // Add the PDT to allocated stock (the actual deduction amount will be tracked separately)
            allocatedStock.add(pdt);
            remainingRequired -= amountToDeduct;

            log.info("Allocated {} tons from PDT: {} (delivery time: {}), remaining required: {} tons", 
                amountToDeduct, pdt.getPdtId(), pdt.getDeliveryTime(), remainingRequired);
        }

        log.info("FIFO allocation completed. Allocated {} PDTs, total allocated: {} tons", allocatedStock.size(), requiredAmount - remainingRequired);

        return allocatedStock;
    }
    
    /**
     * Calculate the actual amount to deduct from each PDT based on FIFO strategy
     * Returns a map of PDT ID to the amount that should be deducted
     */
    public static Map<UUID, Double> calculateDeductionAmounts(List<PayloadDeliveryTicket> availableStock, double requiredAmount) {
        Map<UUID, Double> deductionAmounts = new HashMap<>();
        double remainingRequired = requiredAmount;

        for (PayloadDeliveryTicket pdt : availableStock) { // Oldest first
            if (remainingRequired <= 0) {
                break;
            }

            double pdtWeight = pdt.getPayloadWeight();
            double amountToDeduct = Math.min(remainingRequired, pdtWeight);

            deductionAmounts.put(pdt.getPdtId(), amountToDeduct);
            remainingRequired -= amountToDeduct;

            log.info("Calculated deduction amount for PDT: {} (delivery time: {}): {} tons", 
                pdt.getPdtId(), pdt.getDeliveryTime(), amountToDeduct);
        }

        return deductionAmounts;
    }
    
    public static double calculateTotalAvailableStock(List<PayloadDeliveryTicket> availableStock) {
        return availableStock.stream()
            .mapToDouble(PayloadDeliveryTicket::getPayloadWeight)
            .sum();
    }
} 