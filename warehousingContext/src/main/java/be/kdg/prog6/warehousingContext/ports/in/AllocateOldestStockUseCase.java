package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import java.util.List;
import java.util.UUID;

public interface AllocateOldestStockUseCase {
    List<PayloadDeliveryTicket> allocateOldestStockForLoading(String rawMaterialName, double requiredAmount);
    void allocateAndDeductOldestStockForShippingOrder(UUID shippingOrderId, String purchaseOrderReference);
} 