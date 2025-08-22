package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.DeliverPayloadRequestDto;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PayloadDeliveryMapper {
    
    public DeliverPayloadCommand toCommand(DeliverPayloadRequestDto dto) {
        return new DeliverPayloadCommand(
            dto.getLicensePlate(),
            dto.getRawMaterialName(),
            dto.getWarehouseNumber(),
            dto.getPayloadWeight(),
            dto.getSellerId(),
            dto.getDeliveryTime() != null ? dto.getDeliveryTime() : LocalDateTime.now(),
            dto.getNewWeighingBridgeNumber()
        );
    }
} 