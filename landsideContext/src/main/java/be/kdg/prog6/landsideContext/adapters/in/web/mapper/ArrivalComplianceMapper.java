package be.kdg.prog6.landsideContext.adapters.in.web.mapper;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.ArrivalComplianceDto;
import be.kdg.prog6.landsideContext.domain.ArrivalComplianceData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArrivalComplianceMapper {
    
    public List<ArrivalComplianceDto> toDtoList(List<ArrivalComplianceData> complianceData) {
        return complianceData.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
    
    private ArrivalComplianceDto toDto(ArrivalComplianceData data) {
        return new ArrivalComplianceDto(
            data.getAppointmentId(),
            data.getLicensePlate(),
            data.getSellerId(),
            data.getRawMaterial(),
            data.getScheduledArrivalTime(),
            data.getActualArrivalTime(),
            data.getStatus(),
            data.isOnTime()
        );
    }
} 