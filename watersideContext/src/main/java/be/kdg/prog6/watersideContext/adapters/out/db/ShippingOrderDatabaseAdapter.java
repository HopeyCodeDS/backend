package be.kdg.prog6.watersideContext.adapters.out.db;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShippingOrderDatabaseAdapter implements ShippingOrderRepositoryPort {
    
    private final ShippingOrderJpaRepository jpaRepository;
    private final ShippingOrderJpaMapper mapper;
    
    @Override
    public void save(ShippingOrder shippingOrder) {
        ShippingOrderJpaEntity entity = mapper.toJpaEntity(shippingOrder);
        jpaRepository.save(entity);
    }
    
    @Override
    public Optional<ShippingOrder> findByShippingOrderNumber(String shippingOrderNumber) {
        return jpaRepository.findByShippingOrderNumber(shippingOrderNumber)
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<ShippingOrder> findByVesselNumber(String vesselNumber) {
        return jpaRepository.findByVesselNumber(vesselNumber)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<ShippingOrder> findByStatus(ShippingOrder.ShippingOrderStatus status) {
        return jpaRepository.findByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
    
    @Override
    public List<ShippingOrder> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ShippingOrder> findById(UUID shippingOrderId) {
        return jpaRepository.findById(shippingOrderId.toString())
                .map(mapper::toDomain);
    }
}