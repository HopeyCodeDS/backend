package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.TruckJpaEntity;
import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.Truck;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TruckMapper {

    TruckJpaEntity domainToEntity(Truck truck);

    @InheritInverseConfiguration
    Truck entityToDomain(TruckJpaEntity truckJpaEntity);

    // Custom mapping methods for LicensePlate
    default LicensePlate map(String licensePlateValue) {
        return new LicensePlate(licensePlateValue);
    }

    default String map(LicensePlate licensePlate) {
        return licensePlate.plateNumber();
    }

}
