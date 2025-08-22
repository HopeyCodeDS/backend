package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.AppointmentStatus;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.commands.RecognizeTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.GateControlPort;
import be.kdg.prog6.landsideContext.ports.out.TruckArrivedPort;
import be.kdg.prog6.landsideContext.ports.out.TruckEnteredGatePort;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {
    
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    private final GateControlPort gateControlPort;
    private final TruckArrivedPort truckArrivedPort;
    private final TruckEnteredGatePort truckEnteredGatePort;
    
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
        
        // Publish event through output port
        truckArrivedPort.truckArrived(appointment);

        // Create truck movement
        TruckMovement truckMovement = TruckMovement.startAtGate(
            appointment.getTruck().getLicensePlate(),
            command.getRecognitionTime()
        );

        // Save truck movement
        truckMovementRepositoryPort.save(truckMovement);

        // Open the gate
        gateControlPort.openGate();

        // Publish event through output port
        truckEnteredGatePort.truckEnteredGate(truckMovement);
        
        return true;
    }
} 