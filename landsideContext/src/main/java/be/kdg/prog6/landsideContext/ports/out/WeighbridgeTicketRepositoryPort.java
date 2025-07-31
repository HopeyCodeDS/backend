package be.kdg.prog6.landsideContext.ports.out;

import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WeighbridgeTicketRepositoryPort {
    WeighbridgeTicket save(WeighbridgeTicket ticket);
    Optional<WeighbridgeTicket> findById(UUID ticketId);
    List<WeighbridgeTicket> findByLicensePlate(String licensePlate);
    List<WeighbridgeTicket> findAll();
} 