package be.kdg.prog6.watersideContext.ports.out;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;

public interface ShippingOrderSubmittedPort {
    void publishShippingOrderSubmitted(ShippingOrder shippingOrder);
}