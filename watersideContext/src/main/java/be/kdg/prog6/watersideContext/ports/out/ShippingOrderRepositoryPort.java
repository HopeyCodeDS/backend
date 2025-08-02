package be.kdg.prog6.watersideContext.ports.out;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import java.util.List;
import java.util.Optional;

public interface ShippingOrderRepositoryPort {
    void save(ShippingOrder shippingOrder);
    Optional<ShippingOrder> findByShippingOrderNumber(String shippingOrderNumber);
    Optional<ShippingOrder> findByVesselNumber(String vesselNumber);
    List<ShippingOrder> findByStatus(ShippingOrder.ShippingOrderStatus status);
    List<ShippingOrder> findAll();
}
