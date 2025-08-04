package be.kdg.prog6.warehousingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayloadDeliveryTicketRepository extends JpaRepository<PayloadDeliveryTicketJpaEntity, UUID> {
    Optional<PayloadDeliveryTicketJpaEntity> findByLicensePlate(String licensePlate);
    List<PayloadDeliveryTicketJpaEntity> findAll();
    List<PayloadDeliveryTicketJpaEntity> findByRawMaterialNameOrderByDeliveryTimeAsc(String rawMaterialName);
} 