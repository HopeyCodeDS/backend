package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {

private final AppointmentRepositoryPort appointmentRepositoryPort;

    public RecognizeTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @Override
    public Optional<Appointment> recognizeTruck(String licensePlate) {
        // Check for an appointment using the license plate
        return appointmentRepositoryPort.findByLicensePlate(licensePlate); // Implement this method in the repository
    }
}
