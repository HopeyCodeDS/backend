package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WeighbridgeTicketDatabaseAdapter implements WeighbridgeTicketRepositoryPort {
    
    private final WeighbridgeTicketRepository repository;
    private final WeighbridgeTicketDatabaseMapper mapper;
    
    @Override
    public WeighbridgeTicket save(WeighbridgeTicket ticket) {
        WeighbridgeTicketJpaEntity entity = mapper.toEntity(ticket);
        WeighbridgeTicketJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<WeighbridgeTicket> findById(UUID ticketId) {
        return repository.findById(ticketId).map(mapper::toDomain);
    }
    
    @Override
    public List<WeighbridgeTicket> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlateOrderByWeighingTimeDesc(licensePlate)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<WeighbridgeTicket> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}