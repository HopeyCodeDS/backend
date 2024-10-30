package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.common.domain.SellerID;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.CalendarRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.SlotRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class CreateAppointmentUseCaseImpl implements CreateAppointmentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateAppointmentUseCaseImpl.class);

    private final AppointmentRepositoryPort appointmentRepository;
    private final SlotRepositoryPort slotRepository;
    private final TruckRepositoryPort truckRepository;
    private final CalendarRepositoryPort calendarRepository;

    public CreateAppointmentUseCaseImpl(AppointmentRepositoryPort appointmentRepository,
                                        SlotRepositoryPort slotRepository,
                                        TruckRepositoryPort truckRepository,
                                        CalendarRepositoryPort calendarRepository) {
        this.appointmentRepository = appointmentRepository;
        this.slotRepository = slotRepository;
        this.truckRepository = truckRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    @Transactional
    public Appointment createAppointment(CreateAppointmentCommand command) {

        // 1. Validate material type and other fields
        validateMaterialType(command.materialType());

        // 2. Find or create truck
        Truck truck = truckRepository.findTruckByLicensePlate(command.license_plate())
                .orElseGet(() -> createAndSaveNewTruck(command));

        // 3. Find or create slot
        Slot slot = findOrCreateSlot(command.arrivalTime());

        // 4. Create Appointment
        Appointment appointment = new Appointment(
                truck,
                command.arrivalTime(),
                new SellerID(command.sellerId()),
                command.materialType(),
                slot
        );

        // 5. Save Appointment
        appointmentRepository.saveAppointment(appointment);

        logger.info("Created new appointment for truck {} at arrival window {}", truck.getLicensePlate(), command.arrivalTime());
        logger.info("Appointment created for truck {} with slot ID {}", truck.getLicensePlate(), slot.getId());
        return appointment;
    }

    private Truck createAndSaveNewTruck(CreateAppointmentCommand command) {
        if (command.license_plate() == null || command.license_plate().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null.");
        }
        Truck newTruck = new Truck(command.license_plate(), command.materialType());
        truckRepository.save(newTruck);  // Persist new truck
        logger.info("Created new truck with license plate: {}", command.license_plate());
        return newTruck;
    }

private Slot findOrCreateSlot(LocalDateTime arrivalTime) {
    // Check in the repository if an existing slot has the same start time and has capacity
    Optional<Slot> availableSlot = slotRepository.findSlotByStartTime(arrivalTime)
            .filter(slot -> slot.getScheduledTrucks().size() < Slot.MAXIMUM_CAPACITY );

    // If an available slot exists, return it; otherwise, create a new one
    if (availableSlot.isPresent()) {
        logger.info("Reusing existing slot with start time: {}", arrivalTime);
        return availableSlot.get();
    } else {
        // Create a new slot only if no existing slot is available
        Slot newSlot = new Slot(arrivalTime, arrivalTime.plusHours(1));
        slotRepository.saveSlot(newSlot);  // Persist the new slot with auto-incremented ID
        logger.info("Created new slot for arrival time: {}", arrivalTime);
        return newSlot;
    }
}

    private void validateMaterialType(MaterialType materialType) {
        if (materialType == null) {
            throw new IllegalArgumentException("Material type cannot be null.");
        }
    }
}
