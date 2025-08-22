package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.common.events.CommissionFeeCalculatedEvent;

public interface CommissionFeeCalculatedEventPublisherPort {
    void publishCommissionFeeCalculatedEvent(CommissionFeeCalculatedEvent event);
} 