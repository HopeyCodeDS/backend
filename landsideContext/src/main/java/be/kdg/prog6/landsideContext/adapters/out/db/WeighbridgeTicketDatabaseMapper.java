package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import org.springframework.stereotype.Component;

@Component
public class WeighbridgeTicketDatabaseMapper {
    
    public WeighbridgeTicketJpaEntity toEntity(WeighbridgeTicket ticket) {
        WeighbridgeTicketJpaEntity entity = new WeighbridgeTicketJpaEntity();
        entity.setTicketId(ticket.getTicketId());
        entity.setLicensePlate(ticket.getLicensePlate());
        entity.setGrossWeight(ticket.getGrossWeight());
        entity.setTareWeight(ticket.getTareWeight());
        entity.setNetWeight(ticket.getNetWeight());
        entity.setWeighingTime(ticket.getWeighingTime());
        return entity;
    }
    
    public WeighbridgeTicket toDomain(WeighbridgeTicketJpaEntity entity) {
        return new WeighbridgeTicket(
            entity.getTicketId(),
            entity.getLicensePlate(),
            entity.getGrossWeight(),
            entity.getTareWeight(),
            entity.getNetWeight(),
            entity.getWeighingTime()
        );
    }
} 