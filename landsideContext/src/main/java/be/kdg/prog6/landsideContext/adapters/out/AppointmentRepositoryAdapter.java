package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.AppointmentJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.AppointmentJpaRepository;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.common.domain.SellerID;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.SlotRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryAdapter implements AppointmentRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentRepositoryAdapter.class);

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final SlotRepositoryPort slotRepositoryPort;
    private final TruckRepositoryPort truckRepository;

    public AppointmentRepositoryAdapter(AppointmentJpaRepository appointmentJpaRepository,
                                        SlotRepositoryPort slotRepositoryPort,
                                        TruckRepositoryPort truckRepository) {
        this.appointmentJpaRepository = appointmentJpaRepository;
        this.slotRepositoryPort = slotRepositoryPort;
        this.truckRepository = truckRepository;
    }

    @Override
    public void save(Appointment appointment) {
        logger.info("Saving appointment for truck: {}", appointment.getTruck().getLicensePlate());
        AppointmentJpaEntity entity = mapToJpaEntity(appointment);
        appointmentJpaRepository.save(entity);
    }

    @Override
    public Optional<Appointment> findBySellerId(UUID sellerId) {
        logger.info("Searching for appointment with seller ID: {}", sellerId);
        return appointmentJpaRepository.findBySellerId(sellerId.toString())
                .map(this::mapToDomain);
    }

    @Override
    public List<Appointment> findAppointmentsDuringArrivalWindow(LocalDateTime start, LocalDateTime end) {
        logger.info("Searching for appointments between {} and {}", start, end);
        return appointmentJpaRepository.findByArrivalWindowBetween(start, end)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findAppointmentsByTruckLicensePlate(String licensePlate) {
        logger.info("Searching for appointments by truck license plate: {}", licensePlate);
        return appointmentJpaRepository.findAppointmentsWithSlotByTruckLicensePlate(licensePlate)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> findByLicensePlate(String licensePlate) {
        logger.info("Searching for appointment by truck license plate: {}", licensePlate);
        return appointmentJpaRepository.findAppointmentsWithSlotByTruckLicensePlate(licensePlate)
                .map(this::mapToDomain);
    }


//    public Optional<Appointment> findTruckByLicensePlate(String licensePlate) {
//        logger.info("Searching for appointment by truck license plate: {}", licensePlate);
//        return appointmentJpaRepository.findAppointmentsWithSlotByTruckLicensePlate(licensePlate)
//                .map(this::mapToDomain);
//    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        AppointmentJpaEntity entity = mapToJpaEntity(appointment);
        AppointmentJpaEntity savedEntity = appointmentJpaRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    // Manual Mapping: Appointment -> AppointmentJpaEntity
    private AppointmentJpaEntity mapToJpaEntity(Appointment appointment) {
        AppointmentJpaEntity entity = new AppointmentJpaEntity();

        entity.setSellerId(appointment.getSellerId().getUuid().toString());  // Store UUID as string in DB
        entity.setArrivalWindow(appointment.getArrivalWindow());
        entity.setMaterialType(appointment.getMaterialType());

        // Find or create TruckJpaEntity
        TruckJpaEntity truckEntity = truckRepository.findTruckByLicensePlate(appointment.getTruck().getLicensePlate())
                .map(this::mapTruckToJpaEntity)
                .orElseGet(() -> {
                    TruckJpaEntity newTruckEntity = new TruckJpaEntity();
                    newTruckEntity.setLicensePlate(appointment.getTruck().getLicensePlate());
                    newTruckEntity.setMaterialType(appointment.getTruck().getMaterialType());
                    truckRepository.save(new Truck(newTruckEntity.getLicensePlate(), newTruckEntity.getMaterialType()));  // Persist truck if new
                    return newTruckEntity;
                });
        entity.setTruck(truckEntity);

        // Find or create SlotJpaEntity
        SlotJpaEntity slotEntity = slotRepositoryPort.findSlotByStartTime(appointment.getSlot().getStartTime())
                .map(this::mapSlotToJpaEntity)
                .orElseGet(() -> {
                    SlotJpaEntity newSlotEntity = new SlotJpaEntity();
                    newSlotEntity.setStartTime(appointment.getSlot().getStartTime());
                    newSlotEntity.setEndTime(appointment.getSlot().getEndTime());
                    slotRepositoryPort.saveSlot(new Slot(newSlotEntity.getStartTime(), newSlotEntity.getEndTime()));
                    return newSlotEntity;
                });
        entity.setSlot(slotEntity);

        return entity;
    }

    // Manual Mapping: AppointmentJpaEntity -> Appointment
    private Appointment mapToDomain(AppointmentJpaEntity entity) {
        Truck truck = new Truck(entity.getTruck().getLicensePlate(), entity.getTruck().getMaterialType());
        SellerID sellerId = new SellerID(UUID.fromString(entity.getSellerId()));
        Slot slot = new Slot(entity.getSlot().getStartTime(), entity.getSlot().getEndTime());

        return new Appointment(truck, entity.getArrivalWindow(), sellerId, entity.getMaterialType(), slot);
    }

    // Helper methods for Truck and Slot mapping
    private TruckJpaEntity mapTruckToJpaEntity(Truck truck) {
        TruckJpaEntity truckJpaEntity = new TruckJpaEntity();
        truckJpaEntity.setLicensePlate(truck.getLicensePlate());
        truckJpaEntity.setMaterialType(truck.getMaterialType());
        return truckJpaEntity;
    }

    private SlotJpaEntity mapSlotToJpaEntity(Slot slot) {
        SlotJpaEntity slotJpaEntity = new SlotJpaEntity();
        slotJpaEntity.setStartTime(slot.getStartTime());
        slotJpaEntity.setEndTime(slot.getEndTime());
        return slotJpaEntity;
    }
}
