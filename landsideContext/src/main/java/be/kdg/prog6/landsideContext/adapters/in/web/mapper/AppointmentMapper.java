package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ScheduleAppointmentRequestDto;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import org.springframework.stereotype.Component;

@Component("appointmentWebMapper")
public class AppointmentMapper {
    
    public ScheduleAppointmentCommand toCommand(ScheduleAppointmentRequestDto requestDto) {
        return new ScheduleAppointmentCommand(
            requestDto.getSellerId(),
            requestDto.getLicensePlate(),
            requestDto.getTruckType(),
            requestDto.getRawMaterialName(),
            requestDto.getArrivalTime()
        );
    }
} 