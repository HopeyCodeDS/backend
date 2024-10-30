package be.kdg.prog6.warehousingContext.adapters.out;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayloadDeliveryTicketJpaRepository extends JpaRepository<PayloadDeliveryTicket, Long> {
    Optional<PayloadDeliveryTicket> findByLicensePlate(String licensePlate);
}
