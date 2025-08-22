package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.RawMaterial;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.Truck.TruckType;
import be.kdg.prog6.landsideContext.domain.commands.UpdateAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.UpdateAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateAppointmentUseCaseImpl implements UpdateAppointmentUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public Appointment updateAppointment(UpdateAppointmentCommand command) {
        log.info("Updating appointment with ID: {}", command.getAppointmentId());
        
        // Get the existing appointment
        Appointment existingAppointment = appointmentRepositoryPort.findById(command.getAppointmentId())
            .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + command.getAppointmentId()));
        
        // Create updated appointment with new values
        Appointment updatedAppointment = createUpdatedAppointment(existingAppointment, command);
        
        // Save the updated appointment
        appointmentRepositoryPort.save(updatedAppointment);
        
        log.info("Successfully updated appointment with ID: {}", command.getAppointmentId());
        return updatedAppointment;
    }
    
    private Appointment createUpdatedAppointment(Appointment existing, UpdateAppointmentCommand command) {
        // Create new truck if license plate or truck type changed
        Truck truck = existing.getTruck();
        if (command.getLicensePlate() != null || command.getTruckType() != null) {
            String licensePlate = command.getLicensePlate() != null ? command.getLicensePlate() : existing.getTruck().getLicensePlate().getValue();
            TruckType truckType = command.getTruckType() != null ? command.getTruckType() : existing.getTruck().getTruckType();
            truck = new Truck(UUID.randomUUID(), new LicensePlate(licensePlate), truckType);
        }
        
        // Create new raw material if name changed
        RawMaterial rawMaterial = existing.getRawMaterial();
        if (command.getRawMaterialName() != null) {
            rawMaterial = RawMaterial.fromName(command.getRawMaterialName());
        }
        
        // Create updated appointment
        Appointment updatedAppointment = new Appointment(
            existing.getAppointmentId(),
            existing.getSellerId(),
            existing.getSellerName(),
            truck,
            rawMaterial,
            existing.getArrivalWindow(),
            command.getScheduledTime()
        );
        
        // Update status if provided
        if (command.getStatus() != null) {
            updatedAppointment.setStatus(command.getStatus());
        }
        
        return updatedAppointment;
    }
}
