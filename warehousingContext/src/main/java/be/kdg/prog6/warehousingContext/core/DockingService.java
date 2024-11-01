package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.commands.DockingCommand;
import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.ConveyorBeltAssignmentUseCase;
import be.kdg.prog6.warehousingContext.ports.in.GeneratePDTUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockingService {
    private static final Logger log = LoggerFactory.getLogger(DockingService.class);

    private final ConveyorBeltAssignmentUseCase conveyorBeltAssignmentUseCase;
    private final GeneratePDTUseCase generatePDTUseCase;

    public DockingService(ConveyorBeltAssignmentUseCase conveyorBeltAssignmentUseCase,
                          GeneratePDTUseCase generatePDTUseCase) {
        this.conveyorBeltAssignmentUseCase = conveyorBeltAssignmentUseCase;
        this.generatePDTUseCase = generatePDTUseCase;
    }

    public void processDockingEvent(DockingCommand command) {
        String conveyorBelt = conveyorBeltAssignmentUseCase.assignConveyorBelt(command.getMaterialType());

        // Generate the PDT using the license plate, conveyor belt, and weighing bridge ID
        PayloadDeliveryTicket pdt = generatePDTUseCase.generatePDT(command.getLicensePlate(), conveyorBelt, command.getWeighingBridgeId());

        // Log the generated PDT
        log.info("Generated PDT: {}", pdt);
    }
}
