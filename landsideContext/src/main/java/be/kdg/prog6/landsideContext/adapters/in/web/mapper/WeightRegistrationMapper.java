package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.RegisterWeightRequestDto;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;
import org.springframework.stereotype.Component;

@Component
public class WeightRegistrationMapper {
    
    public RegisterWeightAndExitBridgeCommand toCommand(RegisterWeightRequestDto dto) {
        return new RegisterWeightAndExitBridgeCommand(
                dto.getLicensePlate(),
                dto.getWeight(),
                dto.getRawMaterialName()
        );
    }
} 