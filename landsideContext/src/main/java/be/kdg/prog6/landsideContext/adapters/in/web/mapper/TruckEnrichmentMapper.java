package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckOperationResponseDto;
import be.kdg.prog6.landsideContext.domain.EnrichedTruckData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TruckEnrichmentMapper {
    
    public TruckOperationResponseDto toTruckOperationResponseDto(EnrichedTruckData enrichedTruckData) {
        return new TruckOperationResponseDto(
            enrichedTruckData.truckId(),
            enrichedTruckData.licensePlate(),
            enrichedTruckData.material(),
            enrichedTruckData.plannedArrival(),
            enrichedTruckData.actualArrival(),
            enrichedTruckData.status(),
            enrichedTruckData.sellerId(),
            enrichedTruckData.sellerName(),
            enrichedTruckData.warehouseNumber(),
            enrichedTruckData.grossWeight(),
            enrichedTruckData.tareWeight(),
            enrichedTruckData.netWeight()
        );
    }
    
    public List<TruckOperationResponseDto> toTruckOperationResponseDtoList(List<EnrichedTruckData> enrichedTruckDataList) {
        return enrichedTruckDataList.stream()
            .map(this::toTruckOperationResponseDto)
            .collect(Collectors.toList());
    }
}
