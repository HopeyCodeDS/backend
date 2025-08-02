package be.kdg.prog6.warehousingContext.ports.in;

import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import java.util.List;

public interface GetPurchaseOrderFulfillmentUseCase {
    List<PurchaseOrderFulfillmentTracking> getFulfilledOrders();
    List<PurchaseOrderFulfillmentTracking> getOutstandingOrders();
    List<PurchaseOrderFulfillmentTracking> getOrdersByCustomer(String customerNumber);
    List<PurchaseOrderFulfillmentTracking> getAllOrders();
}
