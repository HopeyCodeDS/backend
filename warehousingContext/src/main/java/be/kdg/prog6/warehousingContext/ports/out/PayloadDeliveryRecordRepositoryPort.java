package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;

import java.util.List;
import java.util.Optional;

public interface PayloadDeliveryRecordRepositoryPort {
    void save(PayloadDeliveryTicket pdt);

    List<PayloadDeliveryTicket> findAll();

    Optional<PayloadDeliveryTicket> findByLicensePlate(String licensePlate);
}
