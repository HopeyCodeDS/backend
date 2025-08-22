package be.kdg.prog6.invoicingContext.domain.commands;

import java.util.UUID;

public record CalculateCommissionCommand(
    String purchaseOrderNumber,
    String customerNumber, 
    UUID sellerId,
    double totalValue
) {}