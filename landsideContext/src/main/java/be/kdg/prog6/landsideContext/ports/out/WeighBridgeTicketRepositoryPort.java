package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;

public interface WeighBridgeTicketRepositoryPort {
    void save(WeighBridgeTicket weighBridgeTicket);
}
