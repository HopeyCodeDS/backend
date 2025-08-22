package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetOutstandingInspectionsUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetOutstandingInspectionsUseCaseImpl implements GetOutstandingInspectionsUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public List<ShippingOrder> getOutstandingInspections() {
        log.info("Inspector requesting outstanding inspections");
        
        List<ShippingOrder> allShippingOrders = shippingOrderRepositoryPort.findAll();
        
        List<ShippingOrder> outstandingInspections = allShippingOrders.stream()
                .filter(so -> !so.getInspectionOperation().isCompleted())
                .collect(Collectors.toList());
        
        log.info("Found {} outstanding inspections", outstandingInspections.size());
        
        return outstandingInspections;
    }
} 