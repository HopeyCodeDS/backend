package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetShipCaptainOperationsOverviewUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetShipCaptainOperationsOverviewUseCaseImpl implements GetShipCaptainOperationsOverviewUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public ShipCaptainOperationsOverview getOperationsOverview(String vesselNumber) {
        log.info("Ship captain requesting operations overview for vessel: {}", vesselNumber);
        
        Optional<ShippingOrder> shippingOrderOpt = shippingOrderRepositoryPort.findByVesselNumber(vesselNumber);
        
        if (shippingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("No shipping order found for vessel: " + vesselNumber);
        }
        
        ShippingOrder shippingOrder = shippingOrderOpt.get();
        
        String inspectionStatus = shippingOrder.getInspectionOperation().isCompleted() ? "COMPLETED" : "PENDING";
        String bunkeringStatus = shippingOrder.getBunkeringOperation().isCompleted() ? "COMPLETED" : "PENDING";
        
        ShipCaptainOperationsOverview overview = new ShipCaptainOperationsOverview(
            shippingOrder.getShippingOrderId(),
            vesselNumber,
            inspectionStatus,
            bunkeringStatus,
            shippingOrder.getInspectionOperation().getCompletedDate(),
            shippingOrder.getBunkeringOperation().getCompletedDate()
        );
        
        log.info("Ship captain operations overview for vessel {}: can leave port = {}", 
            vesselNumber, overview.isCanLeavePort());
        
        return overview;
    }
} 