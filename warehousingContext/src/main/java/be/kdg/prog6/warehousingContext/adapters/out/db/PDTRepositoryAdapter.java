package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PDTRepositoryAdapter implements PDTRepositoryPort {
    
    private final PayloadDeliveryTicketRepository repository;
    private final PDTMapper mapper;
    
    @Override
    public PayloadDeliveryTicket save(PayloadDeliveryTicket pdt) {
        PayloadDeliveryTicketJpaEntity entity = mapper.toEntity(pdt);
        PayloadDeliveryTicketJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<PayloadDeliveryTicket> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<PayloadDeliveryTicket> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
} 