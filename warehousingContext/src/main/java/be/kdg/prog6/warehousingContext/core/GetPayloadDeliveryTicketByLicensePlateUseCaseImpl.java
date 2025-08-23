package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.GetPayloadDeliveryTicketByLicensePlateUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPayloadDeliveryTicketByLicensePlateUseCaseImpl implements GetPayloadDeliveryTicketByLicensePlateUseCase {

    private final PDTRepositoryPort pdtRepositoryPort;

    @Override
    public PayloadDeliveryTicket getPayloadDeliveryTicketByLicensePlate(String licensePlate) {
        return pdtRepositoryPort.findByLicensePlate(licensePlate).orElse(null);
    }
}