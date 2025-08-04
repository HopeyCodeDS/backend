package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.OldestStockAllocationStrategy;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.AllocateOldestStockUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllocateOldestStockUseCaseImpl implements AllocateOldestStockUseCase {
    
    private final PDTRepositoryPort pdtRepositoryPort;
    
    @Override
    public List<PayloadDeliveryTicket> allocateOldestStockForLoading(String rawMaterialName, double requiredAmount) {
        log.info("Warehouse manager requesting oldest stock allocation for material: {} with amount: {} tons", 
            rawMaterialName, requiredAmount);
        
        // Get all PDTs for the specified material, ordered by delivery time (oldest first)
        List<PayloadDeliveryTicket> availableStock = pdtRepositoryPort
            .findByRawMaterialOrderByDeliveryTimeAsc(rawMaterialName);
        
        log.info("Found {} PDTs for material: {}", availableStock.size(), rawMaterialName);
        log.info("PDTs: {}", availableStock.stream().map(PayloadDeliveryTicket::getDeliveryTime).collect(Collectors.toList()));
        
        // Calculate total available stock using domain logic
        double totalAvailableStock = OldestStockAllocationStrategy.calculateTotalAvailableStock(availableStock);
        
        if (totalAvailableStock < requiredAmount) {
            throw new IllegalStateException(
                String.format("Insufficient stock for material %s. Required: %.2f tons, Available: %.2f tons", 
                    rawMaterialName, requiredAmount, totalAvailableStock)
            );
        }
        
        // Apply domain strategy for FIFO allocation
        List<PayloadDeliveryTicket> allocatedStock = OldestStockAllocationStrategy
            .allocateOldestStockFIFO(availableStock, requiredAmount);
        
        log.info("Successfully allocated {} PDTs for material: {} with total weight: {} tons", 
            allocatedStock.size(), rawMaterialName, 
            allocatedStock.stream().mapToDouble(PayloadDeliveryTicket::getPayloadWeight).sum());
        
        return allocatedStock;
    }
} 