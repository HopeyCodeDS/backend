package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.ports.out.DomainEventPublisher;
import be.kdg.prog6.common.events.WeighingBridgeAssignedEvent;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.AssignWeighingBridgeNumberUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AssignWeighingBridgeNumberUseCaseImpl implements AssignWeighingBridgeNumberUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final TruckRepositoryPort truckRepositoryPort;
    private final DomainEventPublisher eventPublisher;

    public AssignWeighingBridgeNumberUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort,
                                                 TruckRepositoryPort truckRepositoryPort,
                                                 DomainEventPublisher eventPublisher) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
        this.truckRepositoryPort = truckRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public String assignWeighingBridgeNumberToTruck(String licensePlate) {
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
        }

        Appointment appointment = appointmentOpt.get();
        Truck truck = appointment.getTruck();
        log.info("Retrieved the truck {} from the database", truck.getLicensePlate());

        // Check and assign a weighing bridge if not already assigned
        String bridgeNumber = truck.getWeighingBridgeNumber();
        if (bridgeNumber == null || bridgeNumber.isEmpty()) {
            bridgeNumber = determineAvailableBridge();
            truck.assignWeighingBridge(bridgeNumber);
            log.info("Truck {} assigned to the bridge {}", truck.getLicensePlate(), bridgeNumber);

            // Save truck update immediately after assignment
            truckRepositoryPort.save(truck);
            log.info("Truck data updated in the database with the weighing bridge number.");
        }

        // Publish the event to indicate the bridge assignment
        eventPublisher.publish(new WeighingBridgeAssignedEvent(truck.getLicensePlate(), bridgeNumber, LocalDateTime.now()));

        return bridgeNumber;
    }

    private String determineAvailableBridge() {
        return "Bridge-1";
    }
}
