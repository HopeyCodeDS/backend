package be.kdg.prog6.warehousingContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public record WarehouseMaterialShippedEvent(
    @JsonProperty("activityId") UUID activityId,
    @JsonProperty("warehouseId") UUID warehouseId,
    @JsonProperty("amount") double amount,
    @JsonProperty("materialType") String materialType,
    @JsonProperty("shipId") String shipId,
    @JsonProperty("shippedAt") LocalDateTime shippedAt,
    @JsonProperty("description") String description
) {} 