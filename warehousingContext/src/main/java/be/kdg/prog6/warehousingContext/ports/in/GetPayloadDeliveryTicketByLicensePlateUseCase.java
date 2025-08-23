package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;

public interface GetPayloadDeliveryTicketByLicensePlateUseCase {

    PayloadDeliveryTicket getPayloadDeliveryTicketByLicensePlate(String licensePlate);
}