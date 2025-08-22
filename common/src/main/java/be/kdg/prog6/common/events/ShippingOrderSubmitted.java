package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ShippingOrderSubmitted(
    @JsonProperty("shippingOrderNumber") String shippingOrderNumber,
    @JsonProperty("purchaseOrderReference") String purchaseOrderReference,
    @JsonProperty("vesselNumber") String vesselNumber,
    @JsonProperty("customerNumber") String customerNumber,
    @JsonProperty("estimatedArrivalDate") LocalDateTime estimatedArrivalDate,
    @JsonProperty("estimatedDepartureDate") LocalDateTime estimatedDepartureDate
) {} 