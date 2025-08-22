package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import java.util.List;

public interface GetShipmentArrivalsUseCase {
    List<ShippingOrder> getAllShipmentArrivals();
} 