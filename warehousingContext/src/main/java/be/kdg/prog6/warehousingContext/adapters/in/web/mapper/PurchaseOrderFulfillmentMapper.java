package be.kdg.prog6.warehousingContext.adapters.in.web.mapper;

import be.kdg.prog6.warehousingContext.adapters.in.web.dto.PurchaseOrderFulfillmentDto;
import be.kdg.prog6.warehousingContext.adapters.in.web.dto.PurchaseOrderFulfillmentOverviewDto;
import be.kdg.prog6.warehousingContext.domain.PurchaseOrderFulfillmentTracking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderFulfillmentMapper {

    public PurchaseOrderFulfillmentOverviewDto toFulfillmentOverviewDto(
            List<PurchaseOrderFulfillmentTracking> fulfilledOrders, 
            List<PurchaseOrderFulfillmentTracking> outstandingOrders) {
        
        List<PurchaseOrderFulfillmentDto> fulfilledDtos = fulfilledOrders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        
        List<PurchaseOrderFulfillmentDto> outstandingDtos = outstandingOrders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        
        return new PurchaseOrderFulfillmentOverviewDto(
                fulfilledDtos,
                outstandingDtos,
                fulfilledOrders.size(),
                outstandingOrders.size(),
                fulfilledOrders.size() + outstandingOrders.size()
        );
    }

    public PurchaseOrderFulfillmentDto toDto(PurchaseOrderFulfillmentTracking tracking) {
        return new PurchaseOrderFulfillmentDto(
                tracking.getTrackingId().toString(),
                tracking.getPurchaseOrderNumber(),
                tracking.getCustomerNumber(),
                tracking.getCustomerName(),
                tracking.getOrderDate(),
                tracking.getStatus().name(),
                tracking.getTotalValue(),
                tracking.getFulfillmentDate(),
                tracking.getVesselNumber()
        );
    }
} 