package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.facade.GenerateTicketCommand;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;

public interface GenerateWeighBridgeTicketUseCase {
    WeighBridgeTicket generateTicket(GenerateTicketCommand command);
}
