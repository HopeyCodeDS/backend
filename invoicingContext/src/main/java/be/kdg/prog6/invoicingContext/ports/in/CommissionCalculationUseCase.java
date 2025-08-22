package be.kdg.prog6.invoicingContext.ports.in;

import be.kdg.prog6.invoicingContext.domain.commands.CalculateCommissionCommand;

public interface CommissionCalculationUseCase {
    void calculateCommissionForFulfilledOrder(CalculateCommissionCommand command);
}
