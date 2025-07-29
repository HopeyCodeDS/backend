package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.AssignWeighingBridgeRequestDto;
import be.kdg.prog6.landsideContext.domain.commands.AssignWeighingBridgeCommand;
import org.springframework.stereotype.Component;

@Component("weighingBridgeWebMapper")
public class WeighingBridgeMapper {
    
    public AssignWeighingBridgeCommand toAssignWeighingBridgeCommand(AssignWeighingBridgeRequestDto requestDto) {
        return new AssignWeighingBridgeCommand(
            requestDto.getLicensePlate(),
            requestDto.getAssignmentTime()
        );
    }
}