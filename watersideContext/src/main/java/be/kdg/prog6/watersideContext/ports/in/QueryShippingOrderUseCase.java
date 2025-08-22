package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QueryShippingOrderUseCase {
    List<ShippingOrder> getAllShippingOrders();
    Optional<ShippingOrder> getShippingOrderById(UUID shippingOrderId);
    List<ShippingOrder> getShippingOrdersByStatus(ShippingOrder.ShippingOrderStatus status);
    List<ShippingOrder> getShippingOrdersByVessel(String vesselNumber);
    List<ShippingOrder> getArrivedShippingOrders();
    List<ShippingOrder> getLoadingShippingOrders();
    List<ShippingOrder> getDepartedShippingOrders();
    List<ShippingOrder> getReadyForLoadingShippingOrders();
}