package be.kdg.prog6.landsideContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface WeighbridgeTicketRepository extends JpaRepository<WeighbridgeTicketJpaEntity, UUID> {
    List<WeighbridgeTicketJpaEntity> findByLicensePlateOrderByWeighingTimeDesc(String licensePlate);
} 