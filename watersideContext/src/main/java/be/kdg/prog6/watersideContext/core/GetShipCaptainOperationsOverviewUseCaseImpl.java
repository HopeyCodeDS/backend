package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetShipCaptainOperationsOverviewUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetShipCaptainOperationsOverviewUseCaseImpl implements GetShipCaptainOperationsOverviewUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public ShipCaptainOperationsOverview getVesselOperations(String vesselNumber) {
        log.info("Ship captain requesting operations overview for vessel: {}", vesselNumber);
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findByVesselNumber(vesselNumber);
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("No shipping order found for vessel: " + vesselNumber);
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        ShipCaptainOperationsOverview overview = new ShipCaptainOperationsOverview(
            shippingOrder.getShippingOrderId(),
            vesselNumber,
            shippingOrder.getInspectionOperation().getStatusDescription(),
            shippingOrder.getBunkeringOperation().getStatusDescription(),
            shippingOrder.getInspectionOperation().getCompletedDate(),
            shippingOrder.getBunkeringOperation().getCompletedDate()
        );
        
        log.info("Ship captain operations overview for vessel {}: can leave port = {}", 
            vesselNumber, overview.isCanLeavePort());
        
        return overview;
    }

    @Override
    public List<ShipCaptainOperationsOverview> getAllVesselsOperations() {
        log.info("Ship captain requesting operations overview for all vessels");

        List<ShippingOrder> shippingOrders = shippingOrderRepositoryPort.findAll();
        return shippingOrders.stream()
            .map(this::mapToOperationOverview)
            .collect(Collectors.toList());
    }

    // Helper method to map shipping order to operation overview
    private ShipCaptainOperationsOverview mapToOperationOverview(ShippingOrder shippingOrder) {
        return new ShipCaptainOperationsOverview(
            shippingOrder.getShippingOrderId(),
            shippingOrder.getVesselNumber(),
            shippingOrder.getInspectionOperation().getStatusDescription(),
            shippingOrder.getBunkeringOperation().getStatusDescription(),
            shippingOrder.getInspectionOperation().getCompletedDate(),
            shippingOrder.getBunkeringOperation().getCompletedDate()
        );
    }
} 