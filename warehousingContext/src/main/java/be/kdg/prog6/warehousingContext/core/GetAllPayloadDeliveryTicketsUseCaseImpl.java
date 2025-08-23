package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.ports.in.GetAllPayloadDeliveryTicketsUseCase;
import be.kdg.prog6.warehousingContext.ports.out.PDTRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPayloadDeliveryTicketsUseCaseImpl implements GetAllPayloadDeliveryTicketsUseCase {

    private final PDTRepositoryPort pdtRepositoryPort;

    @Override
    public List<PayloadDeliveryTicket> getAllPayloadDeliveryTickets() {
        return pdtRepositoryPort.findAll();
    }
}