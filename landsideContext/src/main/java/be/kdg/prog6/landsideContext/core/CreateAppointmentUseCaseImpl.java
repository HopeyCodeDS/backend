package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
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
        validateMaterialType(command.materialType());

        // Validate and create seller and truck first
        SellerID sellerIdObject = new SellerID(command.sellerId());
        Truck truck = new Truck(command.plateNumber(), command.materialType()); // Truck constructor accepting plate number and material type

        // Find available slot in the calendar repository with respect to the time.
        Calendar calendar = calendarRepository.getCalendar();
        Optional<Slot> availableSlot = calendar.findAvailableSlot(command.arrivalTime());

        // If no available slot, create a new slot
        Slot slot;
        if (availableSlot.isEmpty()) {
            log.info("No available slot found. Creating a new slot at the given time.");
            slot = createNewSlot(calendar, command.arrivalTime(), truck);  // Method to create a new slot and book the truck
        } else {
            slot = availableSlot.get();
            if (slot.isFull()) {
                throw new IllegalStateException("Slot is full. Cannot book more trucks.");
            }
            calendar.bookSlot(slot, truck); // Book the available slot
        }

        // Create appointment
        Appointment appointment = new Appointment(truck, command.arrivalTime(), sellerIdObject, command.materialType(), slot);
        log.info("Appointment booked: License Plate: {}, Arrival Window: {}", command.plateNumber(), command.arrivalTime());

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
    private Slot createNewSlot(Calendar calendar, LocalDateTime arrivalWindow, Truck truck) {
        if (arrivalWindow == null) {
            throw new IllegalArgumentException("Arrival window cannot be null.");
        }

        try {
            // Creating a new slot with a 1-hour window
            Slot newSlot = new Slot(arrivalWindow, arrivalWindow.plusHours(1));
            calendar.bookSlot(newSlot, truck); // Book the truck into the new slot
            log.info("New slot created and truck booked at {}", arrivalWindow);
            return newSlot;
        } catch (Exception e) {
            log.error("Unable to create a new slot at the given time: {}", arrivalWindow, e);
            throw new IllegalStateException("No available slot at the given time.");
        }
    }

    /**
     * Validate that the material type is not null.
     * @param materialType - The material type of the appointment
     */
    private void validateMaterialType(MaterialType materialType) {
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null.");
        }
    }
}
