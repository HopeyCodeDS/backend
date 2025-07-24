package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.commands.RecognizeTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.GateControlPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final GateControlPort gateControlPort;
    
    @Override
    public boolean recognizeTruck(RecognizeTruckCommand command) {
        // Find appointment by license plate
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort
            .findByLicensePlate(command.getLicensePlate())
            .stream()
            .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
            .findFirst();
        
        if (appointmentOpt.isEmpty()) {
            // No scheduled appointment found for this license plate
            return false;
        }
        
        Appointment appointment = appointmentOpt.get();
        
        // Check if arrival is within the scheduled window
        if (!appointment.getArrivalWindow().isWithinWindow(command.getRecognitionTime())) {
            // Truck arrived outside scheduled window
            return false;
        }
        
        // Mark appointment as arrived
        appointment.markAsArrived(command.getRecognitionTime());
        
        // Save the updated appointment
        appointmentRepositoryPort.save(appointment);
        
        // Open the gate
        gateControlPort.openGate();
        
        return true;
    }
} 