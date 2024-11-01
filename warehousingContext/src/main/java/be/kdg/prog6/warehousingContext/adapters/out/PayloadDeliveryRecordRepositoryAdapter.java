package be.kdg.prog6.warehousingContext.adapters.out;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.out.PayloadDeliveryRecordRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PayloadDeliveryRecordRepositoryAdapter implements PayloadDeliveryRecordRepositoryPort {

    private final PayloadDeliveryTicketJpaRepository deliveryTicketJpaRepository;

    @Autowired
    public PayloadDeliveryRecordRepositoryAdapter(PayloadDeliveryTicketJpaRepository deliveryTicketJpaRepository) {
        this.deliveryTicketJpaRepository = deliveryTicketJpaRepository;
    }

    @Override
    public void save(PayloadDeliveryTicket pdt) {
        deliveryTicketJpaRepository.save(pdt);
    }

    @Override
    public List<PayloadDeliveryTicket> findAll() {
        return deliveryTicketJpaRepository.findAll();
    }

    @Override
    public Optional<PayloadDeliveryTicket> findByLicensePlate(String licensePlate) {
        return deliveryTicketJpaRepository.findByLicensePlate(licensePlate);
    }
}
