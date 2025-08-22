package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetShipmentArrivalsUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetShipmentArrivalsUseCaseImpl implements GetShipmentArrivalsUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public List<ShippingOrder> getAllShipmentArrivals() {
        log.info("Foreman requesting all shipment arrivals");
        
        List<ShippingOrder> allShipmentArrivals = shippingOrderRepositoryPort.findAll();
        
        log.info("Found {} shipment arrivals", allShipmentArrivals.size());
        
        return allShipmentArrivals;
    }
} 