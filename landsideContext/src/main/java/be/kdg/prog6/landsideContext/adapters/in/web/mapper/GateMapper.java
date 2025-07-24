package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckRecognitionRequestDto;
import be.kdg.prog6.landsideContext.domain.commands.RecognizeTruckCommand;
import org.springframework.stereotype.Component;

@Component("gateWebMapper")
public class GateMapper {
    
    public RecognizeTruckCommand toRecognizeTruckCommand(TruckRecognitionRequestDto requestDto) {
        return new RecognizeTruckCommand(
            requestDto.getLicensePlate(),
            requestDto.getRecognitionTime()
        );
    }
} 