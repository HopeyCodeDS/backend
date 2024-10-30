package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.common.events.TruckDispatchedEvent;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.WeighingBridge;
import be.kdg.prog6.landsideContext.ports.in.DockTruckAndGeneratePDTUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckDispatchEventPublisher;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class DockTruckAndGeneratePDTUseCaseImpl implements DockTruckAndGeneratePDTUseCase {

    private final TruckDispatchEventPublisher eventPublisher;
    private final TruckRepositoryPort truckRepositoryPort;

    public DockTruckAndGeneratePDTUseCaseImpl(TruckDispatchEventPublisher eventPublisher,
                                              TruckRepositoryPort truckRepositoryPort) {
        this.eventPublisher = eventPublisher;
        this.truckRepositoryPort = truckRepositoryPort;
    }

    @Override
    @Transactional
    public void dockTruckAndGeneratePDT(String licensePlate) {
        Optional<Truck> truckOpt = truckRepositoryPort.findTruckByLicensePlate(licensePlate);

        if (truckOpt.isEmpty()) {
            throw new IllegalArgumentException("No truck found for license plate: " + licensePlate);
        }

        Truck truck = truckOpt.get();
        log.info("truck weigh status is {}", truck.isWeighed());

        // Ensure the truck has been weighed
        if (!truck.isWeighed()) {
            throw new IllegalArgumentException("Truck must be weighed before docking.");
        }
        // Use the assigned weighing bridge and record the weight
        WeighingBridge weighingBridge = new WeighingBridge(truck.getWeighingBridgeNumber());
        weighingBridge.scanTruckAndRegisterWeight(licensePlate, truck.getWeight());
        log.info("Truck {} registered on weighing bridge {} with weight {}", licensePlate, weighingBridge.getBridgeNumber(), truck.getWeight());

        // Update the truck's weight and save it in the repository
        truck.setWeight(weighingBridge.getTruckGrossWeight());
        truck.setArrivalTime(weighingBridge.getWeighingTime());
        truckRepositoryPort.save(truck);
//        truck.setAssignedConveyorBelt();
//        truckRepositoryPort.save(truck);
        log.info("Updated truck weight in repository for license plate {}", licensePlate);
        log.info("Updated weight in repository for truck = {}", truck.toString());

        // Publish TruckDispatchedEvent with essential details only
        // to trigger conveyor belt assignment in Warehousing Context

        TruckDispatchedEvent event = new TruckDispatchedEvent(
                licensePlate,
                truck.getMaterialType(),
                truck.getWeighingBridgeNumber(),
                truck.getWeight(),
                truck.getArrivalTime());

        eventPublisher.publish(event);

        // Mark the truck as docked and save its state
        truck.dock();
//        truckRepositoryPort.save(truck);
        log.info("Truck {} status saved with docked status", licensePlate);
    }
}
