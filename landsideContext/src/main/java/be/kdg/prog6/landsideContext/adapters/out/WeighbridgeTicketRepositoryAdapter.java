package be.kdg.prog6.landsideContext.adapters.out;

import be.kdg.prog6.landsideContext.adapters.out.db.repositories.WeighbridgeTicketJpaRepository;
import be.kdg.prog6.landsideContext.domain.WeighBridgeTicket;
import be.kdg.prog6.landsideContext.ports.out.WeighBridgeTicketRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class WeighbridgeTicketRepositoryAdapter implements WeighBridgeTicketRepositoryPort {

    private final WeighbridgeTicketJpaRepository jpaRepository;

    public WeighbridgeTicketRepositoryAdapter(WeighbridgeTicketJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(WeighBridgeTicket weighBridgeTicket) {
        jpaRepository.save(weighBridgeTicket);
    }
}
