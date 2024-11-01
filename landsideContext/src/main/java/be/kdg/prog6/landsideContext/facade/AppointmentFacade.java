package be.kdg.prog6.landsideContext.facade;

import be.kdg.prog6.common.events.AppointmentCreatedEvent;
import be.kdg.prog6.landsideContext.ports.in.AppointmentFacadePort;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.core.CreateAppointmentUseCaseImpl;
import be.kdg.prog6.landsideContext.core.GetAppointmentUseCaseImpl;
import be.kdg.prog6.landsideContext.domain.Appointment;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AppointmentFacade implements AppointmentFacadePort {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentFacade.class);

    private final CreateAppointmentUseCaseImpl createAppointmentUseCaseImpl;
    private final GetAppointmentUseCaseImpl getAppointmentUseCaseImpl;

    public AppointmentFacade(CreateAppointmentUseCaseImpl createAppointmentUseCaseImpl, GetAppointmentUseCaseImpl getAppointmentUseCaseImpl) {
        this.createAppointmentUseCaseImpl = createAppointmentUseCaseImpl;
        this.getAppointmentUseCaseImpl = getAppointmentUseCaseImpl;
    }

    @Override
    public AppointmentCreatedEvent createAppointment(CreateAppointmentCommand command) {
        // Create the appointment and get the corresponding event
        Appointment appointment = createAppointmentUseCaseImpl.createAppointment(
                command
        );

        // Generate the event after the appointment has been created
        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                appointment.getTruck().getLicensePlate(),
                appointment.getSellerId().getUuid(),
                appointment.getMaterialType().name(),
                appointment.getArrivalWindow()
        );

        // Log the event dispatch
        dispatchEvent(event);
        return event;
    }

    private void dispatchEvent(AppointmentCreatedEvent event) {
        logger.info("Event Dispatched: Appointment Created for {} at {}", event.getSellerId(), event.getArrivalWindow());
        // Very soon I will apply dispatching logic (like RabbitMQ) here
    }

    @Override
    @Transactional
    public Optional<Appointment> getAppointmentBySellerId(UUID sellerId) {
        logger.info("Getting appointment by sellerId {}", sellerId);
        return getAppointmentUseCaseImpl.getAppointmentBySellerId(sellerId);
    }

    @Override
    public Optional<Appointment> getAppointmentBySellerIdAndMaterialType(UUID sellerId, String materialType) {
        logger.info("Getting appointment by sellerId {} and materialType {}", sellerId, materialType);
        return getAppointmentUseCaseImpl.getAppointmentBySellerIdAndMaterialType(sellerId, materialType);
    }

    @Override
    public List<Appointment> getAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end) {
        logger.info("Getting appointments during arrival window from {} to {}", start, end);
        return getAppointmentUseCaseImpl.getAppointmentsDuringArrivalWindow(start, end);
    }

    @Override
    @Transactional
    public List<Appointment> getAppointmentsByTruckLicensePlate(String licensePlate) {
        logger.info("Getting appointments by license plate {}", licensePlate);
        return getAppointmentUseCaseImpl.getAppointmentsByTruckLicensePlate(licensePlate);
    }

}
