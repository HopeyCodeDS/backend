package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.*;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.AppointmentScheduledPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentUseCaseImpl implements ScheduleAppointmentUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final AppointmentScheduledPort appointmentScheduledPort;
    
    @Override
    public UUID scheduleAppointment(ScheduleAppointmentCommand command) {
        // Validate input
        validateCommand(command);
        
        // Create domain objects
        RawMaterial rawMaterial = RawMaterial.fromName(command.getRawMaterialName());
        ArrivalWindow arrivalWindow = new ArrivalWindow(command.getArrivalTime());
        
        // Create truck
        Truck truck = new Truck(UUID.randomUUID(), command.getTruck().getLicensePlate(), command.getTruck().getTruckType());
        
        // Check capacity (max 40 trucks per hour)
        if (!isCapacityAvailable(arrivalWindow)) {
            throw new IllegalStateException("No capacity available for the requested time slot. Maximum 40 trucks per hour allowed.");
        }
        
        // Create appointment
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = new Appointment(
            appointmentId,
            command.getSellerId(),
            truck,
            rawMaterial,
            arrivalWindow
        );
        
        // Save appointment
        appointmentRepositoryPort.save(appointment);
        
        // Publish event through output port
        appointmentScheduledPort.appointmentScheduled(appointment);
        
        return appointmentId;
    }
    
    private void validateCommand(ScheduleAppointmentCommand command) {
        if (command.getSellerId() == null || command.getSellerId().trim().isEmpty()) {
            throw new IllegalArgumentException("Seller ID is required");
        }
        if (command.getTruck() == null) {
            throw new IllegalArgumentException("Truck is required");
        }
        if (command.getTruck().getLicensePlate() == null || command.getTruck().getLicensePlate().getValue().trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required");
        }
        if (command.getTruck().getTruckType() == null) {
            throw new IllegalArgumentException("Truck type is required");
        }
        if (command.getRawMaterialName() == null || command.getRawMaterialName().trim().isEmpty()) {
            throw new IllegalArgumentException("Raw material name is required");
        }
        if (command.getArrivalTime() == null) {
            throw new IllegalArgumentException("Arrival time is required");
        }
        if (command.getArrivalTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Arrival time cannot be in the past");
        }
    }
    
    private boolean isCapacityAvailable(ArrivalWindow requestedWindow) {
        List<Appointment> existingAppointments = appointmentRepositoryPort.findByArrivalWindow(requestedWindow);
        return existingAppointments.size() < 40; // Max 40 trucks per hour
    }
} 