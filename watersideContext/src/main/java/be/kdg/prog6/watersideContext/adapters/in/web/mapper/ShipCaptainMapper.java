package be.kdg.prog6.watersideContext.adapters.in.web.mapper;

import be.kdg.prog6.watersideContext.adapters.in.web.dto.ShipCaptainOperationsOverviewDto;
import be.kdg.prog6.watersideContext.domain.ShipCaptainOperationsOverview;
import org.springframework.stereotype.Component;

@Component
public class ShipCaptainMapper {
    
    public ShipCaptainOperationsOverviewDto toDto(ShipCaptainOperationsOverview overview) {
        ShipCaptainOperationsOverviewDto dto = new ShipCaptainOperationsOverviewDto();
        dto.setShippingOrderId(overview.getShippingOrderId());
        dto.setVesselNumber(overview.getVesselNumber());
        dto.setInspectionStatus(overview.getInspectionStatus());
        dto.setBunkeringStatus(overview.getBunkeringStatus());
        dto.setCanLeavePort(overview.isCanLeavePort());
        dto.setInspectionCompletedDate(overview.getInspectionCompletedDate());
        dto.setBunkeringCompletedDate(overview.getBunkeringCompletedDate());
        return dto;
    }
} 