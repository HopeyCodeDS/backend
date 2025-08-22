package be.kdg.prog6.landsideContext.adapters.out.db;

import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import org.springframework.stereotype.Component;

@Component
public class TruckMapper {
    
    public TruckJpaEntity toJpaEntity(Truck truck) {
        TruckJpaEntity jpaEntity = new TruckJpaEntity();
        jpaEntity.setTruckId(truck.getTruckId());
        jpaEntity.setLicensePlate(truck.getLicensePlate().getValue());
        jpaEntity.setTruckType(mapTruckType(truck.getTruckType()));
        jpaEntity.setCapacityInTons(truck.getMaxPayloadCapacity());
        return jpaEntity;
    }
    
    public Truck toDomain(TruckJpaEntity jpaEntity) {
        return new Truck(
            jpaEntity.getTruckId(),
            new LicensePlate(jpaEntity.getLicensePlate()),
            mapTruckType(jpaEntity.getTruckType())
        );
    }
    
    private TruckJpaEntity.TruckType mapTruckType(Truck.TruckType domainType) {
        return switch (domainType) {
            case SMALL -> TruckJpaEntity.TruckType.SMALL;
            case MEDIUM -> TruckJpaEntity.TruckType.MEDIUM;
            case LARGE -> TruckJpaEntity.TruckType.LARGE;
        };
    }
    
    private Truck.TruckType mapTruckType(TruckJpaEntity.TruckType jpaType) {
        return switch (jpaType) {
            case SMALL -> Truck.TruckType.SMALL;
            case MEDIUM -> Truck.TruckType.MEDIUM;
            case LARGE -> Truck.TruckType.LARGE;
        };
    }
} 