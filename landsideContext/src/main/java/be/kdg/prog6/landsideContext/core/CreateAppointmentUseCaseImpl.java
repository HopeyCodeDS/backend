package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.domain.SellerID;
import be.kdg.prog6.common.facades.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Calendar;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.CalendarRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    public static final Logger log = LoggerFactory.getLogger(CreateAppointmentUseCaseImpl.class);

    private final CalendarRepositoryPort calendarRepository;
    private final AppointmentRepositoryPort appointmentRepository;

    public CreateAppointmentUseCaseImpl(CalendarRepositoryPort calendarRepository, AppointmentRepositoryPort appointmentRepository) {
        this.calendarRepository = calendarRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(CreateAppointmentCommand command) {

        // Find available slot in the calendar repository with respect to the time.
        Calendar calendar = calendarRepository.getCalendar();
        Optional<Slot> availableSlot = calendar.findAvailableSlot(command.arrivalWindow());

        // If no available slot, create a new slot
        Slot slot;
        if (availableSlot.isEmpty()) {
            log.info("No available slot found. Creating a new slot at the given time.");
            slot = createNewSlot(calendar, command.arrivalWindow());  // Method to create a new slot
        } else {
            slot = availableSlot.get();
            calendar.bookSlot(slot); // Book the available slot
        }

        // Create seller and truck
        SellerID sellerIdObject = new SellerID(command.sellerId());

        // Validate and set MaterialType
        if (command.materialType() == null) {
            throw new IllegalArgumentException("Material type cannot be null.");
        }

        Truck truck = new Truck(command.plateNumber(), command.arrivalWindow(), command.materialType()); // Assuming Truck constructor accepts MaterialType

        // Create appointment
        Appointment appointment = new Appointment(truck, command.arrivalWindow(), sellerIdObject.uuid(), command.materialType(), slot);
        log.info("Appointment booked: License Plate: {}, Arrival Window: {}", command.plateNumber(), command.arrivalWindow());

        // Save the appointment
        appointmentRepository.save(appointment);
        return appointment;
    }

    /**
     * Method to create a new slot if no available slot is found
     * @param calendar - The calendar to which the new slot should be added
     * @param arrivalWindow - The time window when the new slot should be created
     * @return The newly created slot
     */
    private Slot createNewSlot(Calendar calendar, LocalDateTime arrivalWindow) {
        try {
            // Assuming Slot has a constructor that takes start and end time.
            Slot newSlot = new Slot(arrivalWindow, arrivalWindow.plusHours(1)); // Creating a 1-hour slot
            calendar.bookSlot(newSlot); // Method to add the new slot to the calendar
            log.info("New slot created at {}", arrivalWindow);
            return newSlot;
        } catch (Exception e) {
            log.error("Unable to create a new slot at the given time: {}", arrivalWindow, e);
            throw new IllegalStateException("No available slot at the given time.");
        }
    }
}
