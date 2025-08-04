package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.OldestStockAllocationDto;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OldestStockAllocationMapper {
    
    public OldestStockAllocationDto toOldestStockAllocationDto(String rawMaterialName, double requiredAmount, 
                                                              List<PayloadDeliveryTicket> allocatedStock) {
        OldestStockAllocationDto dto = new OldestStockAllocationDto();
        dto.setRawMaterialName(rawMaterialName);
        dto.setRequiredAmount(requiredAmount);
        dto.setAllocatedAmount(allocatedStock.stream()
            .mapToDouble(PayloadDeliveryTicket::getPayloadWeight)
            .sum());
        dto.setAllocatedStock(allocatedStock.stream()
            .map(this::toAllocatedStockItemDto)
            .collect(Collectors.toList()));
        return dto;
    }
    
    private OldestStockAllocationDto.AllocatedStockItemDto toAllocatedStockItemDto(PayloadDeliveryTicket pdt) {
        OldestStockAllocationDto.AllocatedStockItemDto dto = new OldestStockAllocationDto.AllocatedStockItemDto();
        dto.setPdtId(pdt.getPdtId());
        dto.setLicensePlate(pdt.getLicensePlate());
        dto.setWarehouseNumber(pdt.getWarehouseNumber());
        dto.setPayloadWeight(pdt.getPayloadWeight());
        dto.setDeliveryTime(pdt.getDeliveryTime());
        dto.setSellerId(pdt.getSellerId());
        return dto;
    }
} 