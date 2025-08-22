package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseOverviewDto;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WarehouseOverviewMapper {
    
    public WarehouseOverviewDto toWarehouseOverviewDto(List<Warehouse> warehouses, double totalRawMaterial) {
        List<WarehouseOverviewDto.WarehouseDetailDto> warehouseDetails = warehouses.stream()
                .map(this::toWarehouseDetailDto)
                .collect(Collectors.toList());
        
        return new WarehouseOverviewDto(totalRawMaterial, warehouseDetails);
    }
    
    private WarehouseOverviewDto.WarehouseDetailDto toWarehouseDetailDto(Warehouse warehouse) {
        return new WarehouseOverviewDto.WarehouseDetailDto(
                warehouse.getWarehouseNumber(),
                warehouse.getSellerId(),
                warehouse.getAssignedMaterial().getName(),
                warehouse.getCurrentCapacity(),
                warehouse.getMaxCapacity(),
                warehouse.getUtilizationPercentage(),
                warehouse.isAcceptingNewDeliveries()
        );
    }
} 