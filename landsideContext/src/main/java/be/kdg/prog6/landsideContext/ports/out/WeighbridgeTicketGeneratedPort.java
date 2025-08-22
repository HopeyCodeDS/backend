package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;

public interface WeighbridgeTicketGeneratedPort {
    void weighbridgeTicketGenerated(WeighbridgeTicket ticket);
} 