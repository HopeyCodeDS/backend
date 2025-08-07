package be.kdg.prog6.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommissionFeeCalculatedEvent(
    UUID commissionFeeId,
    String purchaseOrderNumber,
    String customerNumber,
    String sellerId,
    double commissionAmount,
    double totalOrderValue,
    LocalDateTime calculationDate
) {} 