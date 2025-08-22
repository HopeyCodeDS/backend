package be.kdg.prog6.watersideContext.core;

import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import be.kdg.prog6.watersideContext.ports.in.GetShipCaptainOperationsOverviewUseCase;
import be.kdg.prog6.watersideContext.ports.out.ShippingOrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetShipCaptainOperationsOverviewUseCaseImpl implements GetShipCaptainOperationsOverviewUseCase {
    
    private final ShippingOrderRepositoryPort shippingOrderRepositoryPort;
    
    @Override
    public ShipCaptainOperationsOverview getVesselOperations(String vesselNumber) {
        log.info("Ship captain requesting operations overview for vessel: {}", vesselNumber);
        
        List<ShippingOrder> shippingOrders = shippingOrderRepositoryPort.findByVesselNumber(vesselNumber);
        
        if (shippingOrders.isEmpty()) {
            log.warn("No shipping orders found for vessel: {}", vesselNumber);
            return new ShipCaptainOperationsOverview(
                UUID.randomUUID(),
                vesselNumber,
                "No operations",
                "No operations",
                null,
                null);
        }
        
        ShippingOrder mostRecentOrder = shippingOrders.stream()
            .max(Comparator.comparing(ShippingOrder::getActualArrivalDate))
            .orElse(shippingOrders.get(0));

        log.info("Found {} shipping orders for vessel: {}. Using most recent: {}", 
            shippingOrders.size(), vesselNumber, mostRecentOrder.getShippingOrderNumber());
        
        ShipCaptainOperationsOverview overview = new ShipCaptainOperationsOverview(
            mostRecentOrder.getShippingOrderId(),
            vesselNumber,
            mostRecentOrder.getInspectionOperation() != null ? 
                mostRecentOrder.getInspectionOperation().getStatusDescription() : "NOT_STARTED",
            mostRecentOrder.getBunkeringOperation() != null ? 
                mostRecentOrder.getBunkeringOperation().getStatusDescription() : "NOT_STARTED",
            mostRecentOrder.getInspectionOperation() != null ? 
                mostRecentOrder.getInspectionOperation().getCompletedDate() : null,
            mostRecentOrder.getBunkeringOperation() != null ? 
                mostRecentOrder.getBunkeringOperation().getCompletedDate() : null
        );
        
        log.info("Ship captain operations overview for vessel {}: can leave port = {}", 
            vesselNumber, overview.isCanLeavePort());
        
        return overview;
    }

    @Override
    public List<ShipCaptainOperationsOverview> getAllVesselsOperations() {
        log.info("Ship captain requesting operations overview for all vessels");

        List<ShippingOrder> shippingOrders = shippingOrderRepositoryPort.findAll();
        Map<String, ShippingOrder> latestOrdersByVessel = shippingOrders.stream()
                .collect(Collectors.groupingBy(
                        ShippingOrder::getVesselNumber,
                        Collectors.maxBy(Comparator.comparing(ShippingOrder::getActualArrivalDate))
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(
                        ShippingOrder::getVesselNumber,
                        order -> order
                ));

        return latestOrdersByVessel.values().stream()
                .map(this::mapToOperationOverview)
                .collect(Collectors.toList());
    }

    // Helper method to map shipping order to operation overview
    private ShipCaptainOperationsOverview mapToOperationOverview(ShippingOrder shippingOrder) {
        // Check if operations exist before calling methods
        String inspectionStatus = "NOT_STARTED";
        String bunkeringStatus = "NOT_STARTED";
        LocalDateTime inspectionCompletedDate = null;
        LocalDateTime bunkeringCompletedDate = null;
        
        if (shippingOrder.getInspectionOperation() != null) {
            inspectionStatus = shippingOrder.getInspectionOperation().getStatusDescription();
            inspectionCompletedDate = shippingOrder.getInspectionOperation().getCompletedDate();
        }
        
        if (shippingOrder.getBunkeringOperation() != null) {
            bunkeringStatus = shippingOrder.getBunkeringOperation().getStatusDescription();
            bunkeringCompletedDate = shippingOrder.getBunkeringOperation().getCompletedDate();
        }
        
        boolean canLeavePort = "COMPLETED".equals(inspectionStatus) && 
                              "COMPLETED".equals(bunkeringStatus);
        
        return new ShipCaptainOperationsOverview(
            shippingOrder.getShippingOrderId(),
            shippingOrder.getVesselNumber(),
            inspectionStatus,
            bunkeringStatus,
            canLeavePort,
            inspectionCompletedDate,
            bunkeringCompletedDate
        );
    }
} 