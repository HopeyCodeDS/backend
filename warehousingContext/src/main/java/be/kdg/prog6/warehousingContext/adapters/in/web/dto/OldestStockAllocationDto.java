package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OldestStockAllocationDto {
    private String rawMaterialName;
    private double requiredAmount;
    private double allocatedAmount;
    private List<AllocatedStockItemDto> allocatedStock;
    
    @Data
    public static class AllocatedStockItemDto {
        private UUID pdtId;
        private String licensePlate;
        private String warehouseNumber;
        private double payloadWeight;
        private LocalDateTime deliveryTime;
        private String sellerId;
    }
} 