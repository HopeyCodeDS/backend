package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetOutstandingBunkeringUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetOutstandingBunkeringUseCaseImpl implements GetOutstandingBunkeringUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public List<ShippingOrder> getOutstandingBunkering() {
        log.info("Bunkering officer requesting outstanding bunkering operations");
        
        List<ShippingOrder> allShippingOrders = shippingOrderRepositoryPort.findAll();
        
        List<ShippingOrder> outstandingBunkering = allShippingOrders.stream()
                .filter(so -> !so.getBunkeringOperation().isCompleted())
                .collect(Collectors.toList());
        
        log.info("Found {} outstanding bunkering operations", outstandingBunkering.size());
        
        return outstandingBunkering;
    }
} 