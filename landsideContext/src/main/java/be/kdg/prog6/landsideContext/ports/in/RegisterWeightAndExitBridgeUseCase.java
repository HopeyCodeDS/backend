package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;

public interface RegisterWeightAndExitBridgeUseCase {
    void registerWeightAndExitBridge(RegisterWeightAndExitBridgeCommand command);
}
