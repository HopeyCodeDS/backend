package be.kdg.prog6.watersideContext.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import be.kdg.prog6.watersideContext.ports.in.ShipDepartedUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import be.kdg.prog6.watersideContext.ports.out.ShipDepartedEventPublisherPort;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.domain.ShippingOrder.ShippingOrderStatus;
import be.kdg.prog6.common.events.ShipDepartedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipDepartedUseCaseImpl implements ShipDepartedUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    private final ShipDepartedEventPublisherPort shipDepartedEventPublisherPort;
    
    @Override
    @Transactional
    public void markShipAsDeparted(String vesselNumber, LocalDateTime departureDate) {
        // Find shipping order by vessel number
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findByVesselNumber(vesselNumber);
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("No shipping order found for vessel: " + vesselNumber);
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        // Only allow departure if ship is ready for loading
        if (shippingOrder.getStatus() != ShippingOrderStatus.READY_FOR_LOADING) {
            throw new IllegalStateException("Ship must be ready for loading before departure. Current status: " + shippingOrder.getStatus());
        }
        
        // Mark as departed
        shippingOrder.markAsDeparted(departureDate);
        shippingOrderRepositoryPort.save(shippingOrder);
        
        // Publish departure event
        ShipDepartedEvent event = new ShipDepartedEvent(
            shippingOrder.getShippingOrderId(),
            shippingOrder.getPurchaseOrderReference(),
            shippingOrder.getVesselNumber(),
            departureDate
        );
        shipDepartedEventPublisherPort.publishShipDepartedEvent(event);

        log.info("Successfully marked ship with vessel number {} as departed.", shippingOrder.getVesselNumber());
    }
}