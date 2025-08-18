package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ScheduleAppointmentRequestDto;
import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("appointmentWebMapper")
public class AppointmentMapper {
    
    public ScheduleAppointmentCommand toCommand(ScheduleAppointmentRequestDto requestDto) {
        // Create Truck
        LicensePlate licensePlate = new LicensePlate(requestDto.getLicensePlate());
        Truck truck = new Truck(UUID.randomUUID(), licensePlate, requestDto.getTruckType());
        
        return new ScheduleAppointmentCommand(
            requestDto.getSellerId(),
            requestDto.getSellerName(),
            truck,
            requestDto.getRawMaterialName(),
            requestDto.getScheduledTime()
        );
    }
} 