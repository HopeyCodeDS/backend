package be.kdg.prog6.invoicingContext.domain.commands;

public record CalculateCommissionCommand(
    String purchaseOrderNumber,
    String customerNumber, 
    String sellerId,
    double totalValue
) {}