package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetUnmatchedShippingOrdersUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUnmatchedShippingOrdersUseCaseImpl implements GetUnmatchedShippingOrdersUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public List<ShippingOrder> getUnmatchedShippingOrders() {
        log.info("Foreman requesting unmatched shipping orders");
        
        List<ShippingOrder> allShippingOrders = shippingOrderRepositoryPort.findAll();
        
        List<ShippingOrder> unmatchedShippingOrders = allShippingOrders.stream()
                .filter(ShippingOrder::isArrived)
                .collect(Collectors.toList());
        
        log.info("Found {} unmatched shipping orders", unmatchedShippingOrders.size());
        
        return unmatchedShippingOrders;
    }
} 