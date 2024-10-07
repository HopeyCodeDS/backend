package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.events.DockingEvent;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.ports.in.DockTruckUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DockTruckUseCaseImpl implements DockTruckUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final RabbitTemplate rabbitTemplate;

    public DockTruckUseCaseImpl(AppointmentRepositoryPort appointmentRepository, RabbitTemplate rabbitTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dockTruck(String licensePlate) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Publish a DockingEvent to RabbitMQ
            DockingEvent event = new DockingEvent(appointment.getSellerId().uuid(), licensePlate, appointment.getMaterialType(), appointment.getTruck().getCurrentWeighingBridgeNumber());
            rabbitTemplate.convertAndSend("dockExchange", "dock.routingKey", event);

            // Save the updated appointment
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
        }
    }


}
