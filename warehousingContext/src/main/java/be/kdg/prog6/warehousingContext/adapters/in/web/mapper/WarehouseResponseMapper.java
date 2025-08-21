package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.WarehouseResponseDto;
import be.kdg.prog6.warehousingContext.domain.Warehouse;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WarehouseResponseMapper {
    
    public WarehouseResponseDto toWarehouseResponseDto(Warehouse warehouse, List<PayloadDeliveryTicket> payloads) {
        return new WarehouseResponseDto(
            warehouse.getWarehouseId().toString(),
            warehouse.getWarehouseNumber(),
            warehouse.getSellerId(),
            "De klant van KDG", // This is a placeholder seller name. Ideally, we would have a seller name in the warehouse entity or a seller entity to get the name from.
            warehouse.getAssignedMaterial().getName(),
            warehouse.getCurrentCapacity(),
            warehouse.getMaxCapacity(),
            payloads.stream()
                .map(this::toPayloadDto)
                .collect(Collectors.toList())
        );
    }
    
    private WarehouseResponseDto.PayloadDto toPayloadDto(PayloadDeliveryTicket pdt) {
        return new WarehouseResponseDto.PayloadDto(
            pdt.getPdtId(),
            pdt.getDeliveryTime(),
            pdt.getPayloadWeight(),
            pdt.getRawMaterialName(),
            pdt.getSellerId()
        );
    }
}
