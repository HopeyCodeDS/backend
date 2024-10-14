package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.commands.DockingCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.DockTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.DockTruckPublisherPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final DockTruckPublisherPort dockTruckPublisher;

    public DockTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepository, DockTruckPublisherPort dockTruckPublisher) {
        this.appointmentRepository = appointmentRepository;
        this.dockTruckPublisher = dockTruckPublisher;
    }


    @Override
    public void dockTruck(String licensePlate) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Publish a DockingEvent via the port
            DockingCommand command = new DockingCommand(appointment.getSellerId().uuid(), licensePlate, appointment.getMaterialType(), appointment.getTruck().getCurrentWeighingBridgeNumber());
            dockTruckPublisher.publishDockingEvent(command);

            // Save the updated appointment
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
        }
    }
}
