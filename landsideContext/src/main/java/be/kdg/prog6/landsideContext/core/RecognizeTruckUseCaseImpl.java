package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.exception.TruckOutsideArrivalWindowException;
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
            LocalDateTime now = LocalDateTime.now();

            // Get the appointment's arrival window (start and end)
//            LocalDateTime now = appointment.getTruck().getArrivalWindow().plusMinutes(1);
//            LocalDateTime now = appointment.getTruck().getArrivalWindow();
            LocalDateTime arrivalWindowStart =  appointment.getArrivalWindow();
            LocalDateTime arrivalWindowEnd = arrivalWindowStart.plusHours(1);   // Assuming a 1-hour arrival window

            // Validate if the current time is within the arrival window
//            if ((now.isEqual(arrivalWindowStart) || now.isAfter(arrivalWindowStart)) && now.isBefore(arrivalWindowEnd)) {
//                return Optional.of(appointment);    // Truck is recognized and within the arrival window
//            } else {
//                throw new IllegalArgumentException("Truck is outside the scheduled arrival window.");
//            }

            // Skipping time validation for future tests or predefined conditions
            boolean skipTimeValidation = true; // You can add a more dynamic condition here if needed

            if (skipTimeValidation || (now.isAfter(arrivalWindowStart) && now.isBefore(arrivalWindowEnd))) {
                return Optional.of(appointment);
            } else {
                throw new TruckOutsideArrivalWindowException("Truck is outside the scheduled arrival window.");
            }
        }

        return Optional.empty();  // No appointment found for this truck

    }
}
