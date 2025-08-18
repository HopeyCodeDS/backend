package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.*;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.AppointmentScheduledPort;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleAppointmentUseCaseImpl implements ScheduleAppointmentUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final AppointmentScheduledPort appointmentScheduledPort;
    private final TruckRepositoryPort truckRepositoryPort;
    
    @Override
    public UUID scheduleAppointment(ScheduleAppointmentCommand command) {
        // Validate input
        validateCommand(command);
        
        // Create domain objects
        RawMaterial rawMaterial = RawMaterial.fromName(command.getRawMaterialName());
        ArrivalWindow arrivalWindow = new ArrivalWindow(command.getScheduledTime());
        
        // Find or create truck
        Truck truck = findOrCreateTruck(command.getTruck());
        
        // Domain handles the capacity check & appointment creation
        Appointment appointment = Appointment.schedule(
                command.getSellerId(),
                command.getSellerName(),
                truck,
                rawMaterial,
                arrivalWindow,
                command.getScheduledTime()
        );
        
        // Save appointment
        appointmentRepositoryPort.save(appointment);
        
        // Publish event through output port
        appointmentScheduledPort.appointmentScheduled(appointment);
        
        return appointment.getAppointmentId();
    }

    private Truck findOrCreateTruck(Truck commandTruck) {
        // Try to find existing truck by license plate
        Optional<Truck> existingTruck = truckRepositoryPort.findByLicensePlate(
                commandTruck.getLicensePlate().getValue()
        );

        return existingTruck.orElseGet(() -> {
            Truck newTruck = new Truck(
                    UUID.randomUUID(),
                    commandTruck.getLicensePlate(),
                    commandTruck.getTruckType()
            );
            truckRepositoryPort.save(newTruck);
            return newTruck;
        });
    }
    
    private void validateCommand(ScheduleAppointmentCommand command) {
        if (command.getSellerId() == null) {
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
        if (command.getRawMaterialName() == null) {
            throw new IllegalArgumentException("Raw material name is required");
        }
        if (command.getScheduledTime() == null) {
            throw new IllegalArgumentException("Scheduled time is required");
        }
        if (command.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Scheduled time cannot be in the past");
        }
    }
} 