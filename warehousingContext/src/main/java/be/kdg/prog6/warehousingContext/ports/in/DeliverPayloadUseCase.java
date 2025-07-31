package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;

public interface DeliverPayloadUseCase {
    PayloadDeliveryTicket deliverPayload(DeliverPayloadCommand command);
} 