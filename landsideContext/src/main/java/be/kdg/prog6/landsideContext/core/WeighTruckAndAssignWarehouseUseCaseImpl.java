package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.domain.events.WarehouseAssignedEvent;
import be.kdg.prog6.landsideContext.ports.in.WeighTruckAndAssignWarehouseUseCase;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.DomainEventPublisher;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class WeighTruckAndAssignWarehouseUseCaseImpl implements WeighTruckAndAssignWarehouseUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final DomainEventPublisher eventPublisher;
    private final TruckRepositoryPort truckRepositoryPort;

    public WeighTruckAndAssignWarehouseUseCaseImpl(AppointmentRepositoryPort appointmentRepositoryPort,
                                                   DomainEventPublisher eventPublisher,
                                                   TruckRepositoryPort truckRepositoryPort) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
        this.eventPublisher = eventPublisher;
        this.truckRepositoryPort = truckRepositoryPort;
    }

    @Override
    @Transactional
    public String weighAndAssignWarehouseToTruck(String licensePlate, double weight) {
        // Retrieve truck and appointment by license plate
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);
        Optional<Truck> truckOpt = truckRepositoryPort.findTruckByLicensePlate(licensePlate);

        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
        }
        if (truckOpt.isEmpty()) {
            throw new IllegalArgumentException("No truck found for license plate: " + licensePlate);
        }

        Truck truck = truckOpt.get();

        // Ensure that the truck in the appointment matches the truck in the truck repository
        if (!appointmentOpt.get().getTruck().getLicensePlate().equals(truck.getLicensePlate())) {
            throw new IllegalArgumentException("Truck in appointment does not match the truck in the truck repository.");
        }

        log.info("Both trucks are the same for license plate: {}", licensePlate);

        // Check if the truck has already been weighed
        if (truck.isWeighed()) {
            throw new IllegalArgumentException("Truck has already been weighed.");
        }

        // Check and assign a weighing bridge if not assigned
        if (truck.getWeighingBridgeNumber() == null || truck.getWeighingBridgeNumber().isEmpty()) {
            String assignedBridgeNumber = assignAvailableBridge();
            truck.assignWeighingBridge(assignedBridgeNumber);
            log.info("Truck {} assigned to weighing bridge {}", licensePlate, assignedBridgeNumber);
        }

        // Use the assigned weighing bridge and record the weight
        WeighingBridge weighingBridge = new WeighingBridge(truck.getWeighingBridgeNumber());
        weighingBridge.scanTruckAndRegisterWeight(truck.getLicensePlate(), weight);
        log.info("Truck {} registered on weighing bridge {} with weight {}", licensePlate, weighingBridge.getBridgeNumber(), weight);

        // Assign warehouse based on material type
        String warehouseNumber = assignWarehouseByMaterialType(truck.getMaterialType());
        truck.assignWarehouse(warehouseNumber);

        // Update the truck's weight and set weighed status
        truck.setWeight(weighingBridge.getTruckGrossWeight());
        truck.setArrivalTime(appointmentOpt.get().getArrivalWindow());
        truck.setAssignedConveyorBelt(truck.getAssignedConveyorBelt());
        truck.setWeighed(truck.isWeighed());
        log.info("Updated Truck Details: {}", truck.toString());
        truck.markAsWeighed();
        System.out.println();
        log.info("Truck {} marked as weighed(having a weight of {})  and assigned to warehouse {}", licensePlate, weighingBridge.getBridgeNumber(), warehouseNumber);

        // Save updated truck to the repository, ensuring all fields are persisted
        truckRepositoryPort.save(truck);
        log.info("Truck data for {} updated in the repository with weighed status = {}, warehouse number = {}, weight = {} and weighing bridge number = {}.",
                licensePlate,
                truck.isWeighed(),
                truck.getWarehouseID(),
                truck.getWeight(),
                truck.getWeighingBridgeNumber());

        // Publish the warehouse assignment event
        eventPublisher.publish(new WarehouseAssignedEvent(licensePlate, warehouseNumber, LocalDateTime.now()));

        return warehouseNumber;
    }
    /// Method to assign warehouse number based on material type
    private String assignWarehouseByMaterialType(MaterialType materialType) {
        // Simple logic for warehouse assignment
        return switch (materialType) {
            case GYPSUM -> "Warehouse-1"; // For Gypsum
            case IRON_ORE -> "Warehouse-2"; // For Iron Ore
            case CEMENT -> "Warehouse-3"; // For Cement
            case PETCOKE -> "Warehouse-4"; // For Petcoke
            case SLAG -> "Warehouse-5"; // For Slag
            default -> "Check another warehouse or speak to the supplier"; // For other materials
        };
    }

    // Method to assign an available weighing bridge if one is not already assigned
    private String assignAvailableBridge() {
        // Simple logic to return a default bridge number. Replace with actual logic as needed.
        return "Bridge-1";
    }

    @Override
    @Transactional
    public WeighBridgeTicket generateWeighBridgeTicket(String licensePlate, double grossWeight, double tareWeight) {
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate);

        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();

            // Ensure the truck hasn't been weighed already
            if (appointment.getTruck().isWeighed()) {
                throw new IllegalArgumentException("Truck has not been weighed.");
            }
            Truck truck = appointment.getTruck();

            // Generate the Weighbridge Ticket (WBT)
            WeighBridgeTicket ticket = new WeighBridgeTicket(licensePlate, truck.getWeight(), tareWeight);

            // Mark the truck as weighed
//            appointment.getTruck().markAsWeighed();
            appointmentRepositoryPort.save(appointment); // Save the updated appointment

            return ticket;
        }

        throw new IllegalArgumentException("No appointment found for truck with license plate: " + licensePlate);
    }
}
