package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckJpaEntity;
import be.kdg.prog6.landsideContext.domain.Truck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TruckMapper {

    @Mapping(source = "licensePlate", target = "licensePlate")
    @Mapping(source = "arrivalWindow", target = "arrivalWindow")
    @Mapping(source = "materialType", target = "materialType")
    @Mapping(source = "warehouseID", target = "warehouseID")
    @Mapping(source = "currentWeighingBridgeNumber", target = "currentWeighingBridgeNumber")
    @Mapping(source = "assignedConveyorBelt", target = "assignedConveyorBelt")
    @Mapping(source = "weighed", target = "weighed")
    TruckJpaEntity domainToEntity(Truck truck);

    @Mapping(source = "licensePlate", target = "licensePlate")
    @Mapping(source = "arrivalWindow", target = "arrivalWindow")
    @Mapping(source = "materialType", target = "materialType")
    @Mapping(source = "warehouseID", target = "warehouseID")
    @Mapping(source = "currentWeighingBridgeNumber", target = "currentWeighingBridgeNumber")
    @Mapping(source = "assignedConveyorBelt", target = "assignedConveyorBelt")
    @Mapping(source = "weighed", target = "weighed")
    Truck entityToDomain(TruckJpaEntity truckJpaEntity);
}
