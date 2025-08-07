package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.CommissionFee;
import org.springframework.stereotype.Component;

@Component
public class CommissionFeeJpaMapper {
    
    public CommissionFeeJpaEntity toJpaEntity(CommissionFee domain) {
        CommissionFeeJpaEntity entity = new CommissionFeeJpaEntity();
        entity.setCommissionFeeId(domain.getCommissionFeeId());
        entity.setPurchaseOrderNumber(domain.getPurchaseOrderNumber());
        entity.setCustomerNumber(domain.getCustomerNumber());
        entity.setSellerId(domain.getSellerId());
        entity.setAmount(domain.getAmount());
        entity.setCalculationDate(domain.getCalculationDate());
        return entity;
    }
    
    public CommissionFee toDomain(CommissionFeeJpaEntity entity) {
        return new CommissionFee(
            entity.getPurchaseOrderNumber(),
            entity.getCustomerNumber(),
            entity.getSellerId(),
            entity.getAmount(),
            entity.getCalculationDate()
        );
    }
} 