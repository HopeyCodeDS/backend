package be.kdg.prog6.watersideContext.adapters.in.web.mapper;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShippingOrderResponseDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.OutstandingInspectionDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.OutstandingBunkeringDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.CompleteBunkeringRequestDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.UnmatchedShippingOrderDto;
import be.kdg.prog6.watersideContext.adapters.in.web.dto.MatchShippingOrderRequestDto;
import be.kdg.prog6.watersideContext.domain.InspectionOperation;
import be.kdg.prog6.watersideContext.domain.BunkeringOperation;
import be.kdg.prog6.watersideContext.domain.ShippingOrder;
import org.springframework.stereotype.Component;

@Component
public class ShippingOrderMapper {
    
    public ShippingOrderResponseDto toShippingOrderResponseDto(ShippingOrder shippingOrder) {
        ShippingOrderResponseDto dto = new ShippingOrderResponseDto();
        dto.setShippingOrderId(shippingOrder.getShippingOrderId());
        dto.setShippingOrderNumber(shippingOrder.getShippingOrderNumber());
        dto.setPurchaseOrderReference(shippingOrder.getPurchaseOrderReference());
        dto.setVesselNumber(shippingOrder.getVesselNumber());
        dto.setCustomerNumber(shippingOrder.getCustomerNumber());
        dto.setEstimatedArrivalDate(shippingOrder.getEstimatedArrivalDate());
        dto.setEstimatedDepartureDate(shippingOrder.getEstimatedDepartureDate());
        dto.setActualArrivalDate(shippingOrder.getActualArrivalDate());
        dto.setStatus(shippingOrder.getStatus().name());
        
        // Check if operations are planned
        dto.setInspectionPlanned(shippingOrder.getInspectionOperation().getStatus() == 
                                InspectionOperation.InspectionStatus.PLANNED);
        dto.setBunkeringPlanned(shippingOrder.getBunkeringOperation().getStatus() == 
                               BunkeringOperation.BunkeringStatus.PLANNED);
        
        return dto;
    }
    
    public OutstandingInspectionDto toOutstandingInspectionDto(ShippingOrder shippingOrder) {
        OutstandingInspectionDto dto = new OutstandingInspectionDto();
        dto.setShippingOrderId(shippingOrder.getShippingOrderId());
        dto.setShippingOrderNumber(shippingOrder.getShippingOrderNumber());
        dto.setVesselNumber(shippingOrder.getVesselNumber());
        dto.setPurchaseOrderReference(shippingOrder.getPurchaseOrderReference());
        dto.setCustomerNumber(shippingOrder.getCustomerNumber());
        dto.setPlannedInspectionDate(shippingOrder.getInspectionOperation().getPlannedDate());
        dto.setActualArrivalDate(shippingOrder.getActualArrivalDate());
        dto.setStatus(shippingOrder.getStatus().name());
        return dto;
    }

    public OutstandingBunkeringDto toOutstandingBunkeringDto(ShippingOrder shippingOrder) {
        OutstandingBunkeringDto dto = new OutstandingBunkeringDto();
        dto.setShippingOrderId(shippingOrder.getShippingOrderId());
        dto.setShippingOrderNumber(shippingOrder.getShippingOrderNumber());
        dto.setVesselNumber(shippingOrder.getVesselNumber());
        dto.setPurchaseOrderReference(shippingOrder.getPurchaseOrderReference());
        dto.setCustomerNumber(shippingOrder.getCustomerNumber());
        dto.setPlannedBunkeringDate(shippingOrder.getBunkeringOperation().getPlannedDate());
        dto.setActualArrivalDate(shippingOrder.getActualArrivalDate());
        dto.setStatus(shippingOrder.getStatus().name());
        return dto;
    }

    public UnmatchedShippingOrderDto toUnmatchedShippingOrderDto(ShippingOrder shippingOrder) {
        UnmatchedShippingOrderDto dto = new UnmatchedShippingOrderDto();
        dto.setShippingOrderId(shippingOrder.getShippingOrderId());
        dto.setShippingOrderNumber(shippingOrder.getShippingOrderNumber());
        dto.setPurchaseOrderReference(shippingOrder.getPurchaseOrderReference());
        dto.setVesselNumber(shippingOrder.getVesselNumber());
        dto.setCustomerNumber(shippingOrder.getCustomerNumber());
        dto.setEstimatedArrivalDate(shippingOrder.getEstimatedArrivalDate());
        dto.setActualArrivalDate(shippingOrder.getActualArrivalDate());
        dto.setStatus(shippingOrder.getStatus().name());
        return dto;
    }
}