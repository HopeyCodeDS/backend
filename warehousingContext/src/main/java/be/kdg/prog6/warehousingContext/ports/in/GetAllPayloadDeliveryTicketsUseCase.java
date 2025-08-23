package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;

import java.util.List;

public interface GetAllPayloadDeliveryTicketsUseCase {

    List<PayloadDeliveryTicket> getAllPayloadDeliveryTickets();
}