package be.kdg.prog6.watersideContext.ports.in;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.commands.UpdateShippingOrderCommand;
import java.util.UUID;

public interface UpdateShippingOrderUseCase {
    ShippingOrder updateShippingOrder(UUID shippingOrderId, UpdateShippingOrderCommand command);
    void deleteShippingOrder(UUID shippingOrderId);
}
