package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {

private final AppointmentRepositoryPort appointmentRepositoryPort;

    public RecognizeTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @Override
    public Optional<Appointment> recognizeTruckAndValidateArrival(String licensePlate) {
        // Check for an appointment using the license plate
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
//            LocalDateTime now = LocalDateTime.now();
            // Is appointed in truck??
            LocalDateTime now = appointment.getArrivalWindow().plusMinutes(1);
            LocalDateTime arrivalWindowStart =  appointment.getTruck().getArrivalWindow();
            LocalDateTime arrivalWindowEnd = arrivalWindowStart.plusDays(1);

            // Validate if the current time is within the arrival window
            if (now.isAfter(arrivalWindowStart) && now.isBefore(arrivalWindowEnd)) {
                return Optional.of(appointment);
            } else {
                throw new IllegalArgumentException("Truck is outside the scheduled arrival window.");
            }
        }

        return Optional.empty();

    }
}
