package be.kdg.prog6.warehousingContext.adapters.out.db;

import be.kdg.prog6.warehousingContext.domain.PayloadDeliveryTicket;
import org.springframework.stereotype.Component;

@Component
public class PDTMapper {
    
    public PayloadDeliveryTicketJpaEntity toEntity(PayloadDeliveryTicket pdt) {
        PayloadDeliveryTicketJpaEntity entity = new PayloadDeliveryTicketJpaEntity();
        entity.setPdtId(pdt.getPdtId().toString()); // Convert UUID to String
        entity.setLicensePlate(pdt.getLicensePlate());
        entity.setRawMaterialName(pdt.getRawMaterialName());
        entity.setWarehouseNumber(pdt.getWarehouseNumber());
        entity.setConveyorBeltNumber(pdt.getConveyorBeltNumber());
        entity.setPayloadWeight(pdt.getPayloadWeight());
        entity.setSellerId(pdt.getSellerId());
        entity.setDeliveryTime(pdt.getDeliveryTime());
        entity.setNewWeighingBridgeNumber(pdt.getNewWeighingBridgeNumber());
        return entity;
    }
    
    public PayloadDeliveryTicket toDomain(PayloadDeliveryTicketJpaEntity entity) {
        return new PayloadDeliveryTicket(
            java.util.UUID.fromString(entity.getPdtId()), // Convert String back to UUID
            entity.getLicensePlate(),
            entity.getRawMaterialName(),
            entity.getWarehouseNumber(),
            entity.getConveyorBeltNumber(),
            entity.getPayloadWeight(),
            entity.getSellerId(),
            entity.getDeliveryTime(),
            entity.getNewWeighingBridgeNumber()
        );
    }
} 