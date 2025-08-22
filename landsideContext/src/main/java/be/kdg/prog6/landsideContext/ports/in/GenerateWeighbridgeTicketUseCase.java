package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.GenerateWeighbridgeTicketCommand;

public interface GenerateWeighbridgeTicketUseCase {
    WeighbridgeTicket generateWeighbridgeTicket(GenerateWeighbridgeTicketCommand command);
} 