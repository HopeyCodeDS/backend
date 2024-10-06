package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.GetWeighingBridgeNumberUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetWeighingBridgeNumberUseCaseImpl implements GetWeighingBridgeNumberUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public GetWeighingBridgeNumberUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @Override
    public String getWeighingBridgeNumber(String licensePlate) {
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            String bridgeNumber = appointment.getWeighingBridgeNumber();

            // If the bridge number is already assigned, return it
            if (bridgeNumber != null && !bridgeNumber.isEmpty()) {
                return bridgeNumber;
            }

            // Assign a new bridge number dynamically (for simplicity, we'll hardcode a bridge number)
            bridgeNumber = assignWeighingBridge();
            appointment.setWeighingBridgeNumber(bridgeNumber); // Update the appointment with the new bridge number
            appointmentRepositoryPort.save(appointment); // Persist the update

            return bridgeNumber;
        }

        throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
    }

    // Logic to assign a weighing bridge dynamically. I can make this more advanced later
    private String assignWeighingBridge() {

        return "Bridge-1";
    }
}
