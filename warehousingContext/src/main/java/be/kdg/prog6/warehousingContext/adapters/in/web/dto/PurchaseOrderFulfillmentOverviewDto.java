package be.kdg.prog6.warehousingContext.adapters.in.web.dto;

import lombok.Data;
import java.util.List;

@Data
public class PurchaseOrderFulfillmentOverviewDto {
    private final List<PurchaseOrderFulfillmentDto> fulfilledOrders;
    private final List<PurchaseOrderFulfillmentDto> outstandingOrders;
    private final int totalFulfilled;
    private final int totalOutstanding;
    private final int totalOrders;
} 