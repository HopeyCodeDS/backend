package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.AssignWarehouseRequestDto;
import be.kdg.prog6.warehousingContext.domain.commands.AssignWarehouseCommand;
import org.springframework.stereotype.Component;

@Component("warehouseWebMapper")
public class WarehouseMapper {
    
    public AssignWarehouseCommand toAssignWarehouseCommand(AssignWarehouseRequestDto requestDto) {
        return new AssignWarehouseCommand(
            requestDto.getLicensePlate(),
            requestDto.getRawMaterialName(),
            requestDto.getSellerId(),
            requestDto.getTruckWeight()
        );
    }
}