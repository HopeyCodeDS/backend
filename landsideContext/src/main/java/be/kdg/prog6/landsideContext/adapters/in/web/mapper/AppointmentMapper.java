package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ScheduleAppointmentRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.UpdateAppointmentRequestDto;
import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateAppointmentCommand;
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

    public UpdateAppointmentCommand toUpdateCommand(UUID appointmentId, UpdateAppointmentRequestDto requestDto) {
        UpdateAppointmentCommand command = new UpdateAppointmentCommand(appointmentId);
        
        if (requestDto.getSellerId() != null) {
            command.setSellerId(requestDto.getSellerId());
        }
        if (requestDto.getLicensePlate() != null) {
            command.setLicensePlate(requestDto.getLicensePlate());
        }
        if (requestDto.getTruckType() != null) {
            command.setTruckType(requestDto.getTruckType());
        }
        if (requestDto.getRawMaterialName() != null) {
            command.setRawMaterialName(requestDto.getRawMaterialName());
        }
        if (requestDto.getStatus() != null) {
            command.setStatus(requestDto.getStatus());
        }
        if (requestDto.getScheduledTime() != null) {
            command.setScheduledTime(requestDto.getScheduledTime());
        }
        
        return command;
    }
} 