package be.kdg.prog6.watersideContext.adapters.in.web.mapper;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShippingOrderResponseDto;
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
}