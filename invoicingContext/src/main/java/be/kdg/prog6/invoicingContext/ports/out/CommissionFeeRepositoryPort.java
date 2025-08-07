package be.kdg.prog6.invoicingContext.ports.out;

import be.kdg.prog6.invoicingContext.domain.CommissionFee;
import java.util.List;

public interface CommissionFeeRepositoryPort {
    void save(CommissionFee commissionFee);
    List<CommissionFee> findByCustomerNumber(String customerNumber);
    List<CommissionFee> findBySellerId(String sellerId);
    List<CommissionFee> findAll();
} 