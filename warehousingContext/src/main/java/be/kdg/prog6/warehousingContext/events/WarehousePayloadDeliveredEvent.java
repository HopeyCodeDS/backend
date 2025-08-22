package be.kdg.prog6.warehousingContext.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public record WarehousePayloadDeliveredEvent(
    @JsonProperty("activityId") UUID activityId,
    @JsonProperty("warehouseId") UUID warehouseId,
    @JsonProperty("amount") double amount,
    @JsonProperty("materialType") String materialType,
    @JsonProperty("licensePlate") String licensePlate,
    @JsonProperty("deliveredAt") LocalDateTime deliveredAt,
    @JsonProperty("description") String description
) {} 