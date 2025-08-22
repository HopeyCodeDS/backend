package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PurchaseOrderSubmitted(
    @JsonProperty("purchaseOrderId") UUID purchaseOrderId,
    @JsonProperty("purchaseOrderNumber") String purchaseOrderNumber,
    @JsonProperty("customerNumber") String customerNumber,
    @JsonProperty("customerName") String customerName,
    @JsonProperty("sellerId") String sellerId,
    @JsonProperty("sellerName") String sellerName,
    @JsonProperty("totalValue") double totalValue,
    @JsonProperty("orderLines") List<PurchaseOrderLine> orderLines,
    @JsonProperty("submittedAt") LocalDateTime submittedAt
) {
    public record PurchaseOrderLine(
        @JsonProperty("rawMaterialName") String rawMaterialName,
        @JsonProperty("amountInTons") double amountInTons,
        @JsonProperty("pricePerTon") double pricePerTon
    ) {}
}