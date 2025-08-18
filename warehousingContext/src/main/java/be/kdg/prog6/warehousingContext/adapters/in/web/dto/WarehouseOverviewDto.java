package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseOverviewDto {
    private double totalRawMaterialInWarehouses;
    private List<WarehouseDetailDto> warehouses;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WarehouseDetailDto {
        private String warehouseNumber;
        private UUID sellerId;
        private String rawMaterialName;
        private double currentCapacity;
        private double maxCapacity;
        private double utilizationPercentage;
        private boolean acceptingNewDeliveries;
    }
} 