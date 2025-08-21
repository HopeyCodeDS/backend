package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckOperationResponseDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.CreateTruckRequestDto;
import be.kdg.prog6.landsideContext.adapters.in.web.dto.UpdateTruckRequestDto;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TruckOperationMapper {
    
    public CreateTruckCommand toCreateCommand(CreateTruckRequestDto requestDto) {
        Truck.TruckType truckType = mapTruckType(requestDto.truckType());
        return new CreateTruckCommand(requestDto.licensePlate(), truckType);
    }
    
    public UpdateTruckCommand toUpdateCommand(UpdateTruckRequestDto requestDto) {
        Truck.TruckType truckType = mapTruckType(requestDto.truckType());
        return new UpdateTruckCommand(requestDto.licensePlate(), truckType);
    }
    
    public TruckOperationResponseDto toResponseDto(Truck truck, 
                                                  Optional<Appointment> appointmentOpt,
                                                  Optional<TruckMovement> movementOpt,
                                                  Optional<WeighbridgeTicket> ticketOpt) {
        
        // Extract data from appointment
        String material = appointmentOpt.map(app -> app.getRawMaterial().getName()).orElse("Unknown");
        LocalDateTime plannedArrival = appointmentOpt.map(Appointment::getScheduledTime).orElse(null);
        UUID sellerId = appointmentOpt.map(Appointment::getSellerId).orElse(null);
        String sellerName = appointmentOpt.map(Appointment::getSellerName).orElse("Unknown");
        
        // Extract data from truck movement
        LocalDateTime actualArrival = movementOpt.map(TruckMovement::getEntryTime).orElse(null);
        String status = movementOpt.map(movement -> movement.getCurrentLocation().name()).orElse("At the Truck Garage");
        String warehouseNumber = movementOpt.map(TruckMovement::getAssignedWarehouse).orElse("Not assigned or unscheduled");
        
        // Extract weight data from weighbridge ticket
        Double grossWeight = ticketOpt.map(WeighbridgeTicket::getGrossWeight).orElse(null);
        Double tareWeight = ticketOpt.map(WeighbridgeTicket::getTareWeight).orElse(null);
        Double netWeight = ticketOpt.map(WeighbridgeTicket::getNetWeight).orElse(null);
        
        return new TruckOperationResponseDto(
            truck.getTruckId(),
            truck.getLicensePlate().getValue(),
            material,
            plannedArrival,
            actualArrival,
            status,
            sellerId,
            sellerName,
            warehouseNumber,
            grossWeight,
            tareWeight,
            netWeight
        );
    }
    
    private Truck.TruckType mapTruckType(String truckTypeStr) {
        return switch (truckTypeStr.toUpperCase()) {
            case "SMALL" -> Truck.TruckType.SMALL;
            case "MEDIUM" -> Truck.TruckType.MEDIUM;
            case "LARGE" -> Truck.TruckType.LARGE;
            default -> throw new IllegalArgumentException("Unknown truck type: " + truckTypeStr);
        };
    }
}
