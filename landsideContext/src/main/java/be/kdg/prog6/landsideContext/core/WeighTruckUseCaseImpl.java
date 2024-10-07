package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.ports.in.WeighTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeighTruckUseCaseImpl implements WeighTruckUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public WeighTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @Override
    public String weighTruck(String licensePlate, double weight) {
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Ensure the truck hasn't been weighed already
            if (appointment.getTruck().isWeighed()) {
                throw new IllegalArgumentException("Truck has already been weighed.");
            }

            // Simulate weighing on the weighing bridge
            WeighingBridge weighingBridge = new WeighingBridge(appointment.getTruck().getCurrentWeighingBridgeNumber());
            weighingBridge.scanTruckAndRegisterWeight(licensePlate, weight);

            // Mark the truck as weighed in the appointment
            appointment.getTruck().markAsWeighed();
            appointmentRepositoryPort.save(appointment); // Save the updated appointment

            return weighingBridge.getAssignedWarehouseNumber(); // Return the warehouse number
        }

        throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
    }

    @Override
    public WeighBridgeTicket generateWeighBridgeTicket(String licensePlate, double grossWeight, double tareWeight) {
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Ensure the truck hasn't been weighed already
//            if (appointment.getTruck().isWeighed()) {
//                throw new IllegalArgumentException("Truck has already been weighed.");
//            }

            // Generate the Weighbridge Ticket (WBT)
            WeighBridgeTicket ticket = new WeighBridgeTicket(licensePlate, grossWeight, tareWeight);

            // Mark the truck as weighed
            appointment.getTruck().markAsWeighed();
            appointmentRepositoryPort.save(appointment); // Save the updated appointment

            return ticket;
        }

        throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
    }
}
