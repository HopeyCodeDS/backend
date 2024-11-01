package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.exception.TruckOutsideArrivalWindowException;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.ports.in.RecognizeTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.GateControlPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class RecognizeTruckUseCaseImpl implements RecognizeTruckUseCase {

private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final GateControlPort gateControlPort;

    public RecognizeTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort, GateControlPort gateControlPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
        this.gateControlPort = gateControlPort;
    }

    @Override
    public Optional<Appointment> recognizeTruckAndValidateArrival(String licensePlate) {
        // Fetch the appointment associated with the truck’s license plate
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            LocalDateTime now = appointment.getArrivalWindow();

            // Get the appointment's arrival window (start and end)
            Slot slot = appointment.getSlot();
            LocalDateTime arrivalWindowStart =  slot.getStartTime();
            LocalDateTime arrivalWindowEnd = slot.getEndTime();

            // Ensure the truck arrives within the specified window
            if (now.isAfter(arrivalWindowStart) && now.isBefore(arrivalWindowEnd)) {
                log.info("The truck with licensePlate {} is within the scheduled arrival window", licensePlate);
                gateControlPort.openGate(licensePlate);  // Use the output port to open the gate
                return Optional.of(appointment);  // Successful recognition and entry
            } else {
                throw new TruckOutsideArrivalWindowException("Truck is outside the scheduled arrival window.");
            }
        }
        return Optional.empty();  // No appointment found

    }
}
