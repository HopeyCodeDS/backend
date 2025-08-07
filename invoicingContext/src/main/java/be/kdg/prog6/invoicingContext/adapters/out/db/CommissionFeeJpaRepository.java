package be.kdg.prog6.invoicingContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommissionFeeJpaRepository extends JpaRepository<CommissionFeeJpaEntity, UUID> {
    List<CommissionFeeJpaEntity> findByCustomerNumber(String customerNumber);
    List<CommissionFeeJpaEntity> findBySellerId(String sellerId);
} 