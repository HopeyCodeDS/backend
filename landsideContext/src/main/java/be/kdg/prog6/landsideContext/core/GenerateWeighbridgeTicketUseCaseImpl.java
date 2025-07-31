package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.GenerateWeighbridgeTicketCommand;
import be.kdg.prog6.landsideContext.ports.in.GenerateWeighbridgeTicketUseCase;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketGeneratedPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateWeighbridgeTicketUseCaseImpl implements GenerateWeighbridgeTicketUseCase {
    
    private final WeighbridgeTicketRepositoryPort weighbridgeTicketRepositoryPort;
    private final WeighbridgeTicketGeneratedPort weighbridgeTicketGeneratedPort;
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    
    @Override
    @Transactional
    public WeighbridgeTicket generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command) {
        log.info("Generating weighbridge ticket for truck: {}", command.licensePlate());

        // Validate truck has exit weighbridge assignment
        validateTruckHasExitWeighbridge(command.licensePlate());
        
        // Create weighbridge ticket
        WeighbridgeTicket ticket = new WeighbridgeTicket(
            command.licensePlate(),
            command.grossWeight(),
            command.tareWeight()
        );
        
        // Validate ticket
        if (!ticket.isValidWeighing()) {
            throw new IllegalStateException("Invalid weighing data for truck: " + command.licensePlate());
        }
        
        // Save ticket
        WeighbridgeTicket savedTicket = weighbridgeTicketRepositoryPort.save(ticket);
        
        // Publish event for other contexts
        weighbridgeTicketGeneratedPort.weighbridgeTicketGenerated(savedTicket);
        
        log.info("Weighbridge ticket generated successfully: {} for truck: {}", 
            savedTicket.getTicketId(), command.licensePlate());
        
        return savedTicket;
    }

    // Validate truck has exit weighing bridge assignment
    private void validateTruckHasExitWeighbridge(String licensePlate) {
        Optional<TruckMovement> truckMovementOpt = truckMovementRepositoryPort.findByLicensePlate(licensePlate);
        
        if (truckMovementOpt.isEmpty()) {
            throw new IllegalStateException("Truck movement not found for license plate: " + licensePlate);
        }
        
        TruckMovement truckMovement = truckMovementOpt.get();
        
        // Check if truck has been assigned an exit weighing bridge (from PDT)
        if (!truckMovement.hasExitWeighbridgeNumber()) {
            throw new IllegalStateException("Truck " + licensePlate + " has not been assigned to an exit weighing bridge. Complete payload delivery first.");
        }
        
        // Check if truck has completed the payload delivery process
        if (truckMovement.getAssignedWarehouse() == null) {
            throw new IllegalStateException("Truck " + licensePlate + " has not completed payload delivery");
        }
        
        log.info("Validation passed: Truck {} has exit weighing bridge assignment: {}", 
            licensePlate, truckMovement.getExitWeighbridgeNumber());
    }
} 