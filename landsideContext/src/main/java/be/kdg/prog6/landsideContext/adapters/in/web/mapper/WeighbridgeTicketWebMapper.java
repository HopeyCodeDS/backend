package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.GenerateWeighbridgeTicketRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.WeighbridgeTicketResponseDto;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.GenerateWeighbridgeTicketCommand;
import org.springframework.stereotype.Component;

@Component
public class WeighbridgeTicketWebMapper {
    
    public GenerateWeighbridgeTicketCommand toCommand(GenerateWeighbridgeTicketRequestDto dto) {
        return new GenerateWeighbridgeTicketCommand(
            dto.licensePlate(),
            dto.grossWeight(),
            dto.tareWeight(),
            dto.weighingTime()
        );
    }
    
    public WeighbridgeTicketResponseDto toResponseDto(WeighbridgeTicket ticket) {
        return new WeighbridgeTicketResponseDto(
            ticket.getTicketId(),
            ticket.getLicensePlate(),
            ticket.getGrossWeight(),
            ticket.getTareWeight(),
            ticket.getNetWeight(),
            ticket.getWeighingTime()
        );
    }
} 