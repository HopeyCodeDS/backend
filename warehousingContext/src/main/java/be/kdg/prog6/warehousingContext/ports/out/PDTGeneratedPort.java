package be.kdg.prog6.warehousingContext.ports.out;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;

public interface PDTGeneratedPort {
    void pdtGenerated(PayloadDeliveryTicket pdt);
} 