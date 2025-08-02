package be.kdg.prog6.watersideContext.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import be.kdg.prog6.watersideContext.domain.ShippingOrder.ShippingOrderStatus;

@Repository
public interface ShippingOrderJpaRepository extends JpaRepository<ShippingOrderJpaEntity, String> {
    Optional<ShippingOrderJpaEntity> findByShippingOrderNumber(String shippingOrderNumber);
    Optional<ShippingOrderJpaEntity> findByVesselNumber(String vesselNumber);
    List<ShippingOrderJpaEntity> findByStatus(ShippingOrderStatus status);
}