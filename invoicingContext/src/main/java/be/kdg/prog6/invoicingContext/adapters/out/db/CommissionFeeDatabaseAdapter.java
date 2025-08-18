package be.kdg.prog6.invoicingContext.adapters.out.db;

import be.kdg.prog6.invoicingContext.domain.CommissionFee;
import be.kdg.prog6.invoicingContext.ports.out.CommissionFeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommissionFeeDatabaseAdapter implements CommissionFeeRepositoryPort {
    
    private final CommissionFeeJpaRepository jpaRepository;
    private final CommissionFeeJpaMapper mapper;
    
    @Override
    public void save(CommissionFee commissionFee) {
        CommissionFeeJpaEntity entity = mapper.toJpaEntity(commissionFee);
        jpaRepository.save(entity);
    }
    
    @Override
    public List<CommissionFee> findByCustomerNumber(String customerNumber) {
        return jpaRepository.findByCustomerNumber(customerNumber)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<CommissionFee> findBySellerId(UUID sellerId) {
        return jpaRepository.findBySellerId(sellerId)
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<CommissionFee> findAll() {
        return jpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
} 