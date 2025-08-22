package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.CommissionFee;
import java.util.List;
import java.util.UUID;

public interface CommissionFeeRepositoryPort {
    void save(CommissionFee commissionFee);
    List<CommissionFee> findByCustomerNumber(String customerNumber);
    List<CommissionFee> findBySellerId(UUID sellerId);
    List<CommissionFee> findAll();
} 