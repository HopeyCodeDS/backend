package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record PurchaseOrderMatchedWithShippingOrderEvent(
    @JsonProperty("purchaseOrderReference") String purchaseOrderReference,
    @JsonProperty("shippingOrderNumber") String shippingOrderNumber,
    @JsonProperty("vesselNumber") String vesselNumber,
    @JsonProperty("customerNumber") String customerNumber,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonProperty("matchedAt") LocalDateTime matchedAt
) {} 