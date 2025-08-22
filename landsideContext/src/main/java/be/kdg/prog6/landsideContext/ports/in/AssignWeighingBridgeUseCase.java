package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.AssignWeighingBridgeCommand;

public interface AssignWeighingBridgeUseCase {
    String assignWeighingBridge(AssignWeighingBridgeCommand command);
} 