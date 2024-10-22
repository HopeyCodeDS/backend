package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.AppointmentJpaEntity;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.common.domain.SellerID;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {TruckMapper.class, SlotMapper.class})
public interface AppointmentMapper {

    @Mapping(source = "truck", target = "truck")
    @Mapping(source = "arrivalWindow", target = "arrivalWindow")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(source = "materialType", target = "materialType")
    @Mapping(source = "slot", target = "slot")
    Appointment entityToDomain(AppointmentJpaEntity appointmentJpaEntity);

    @Mapping(source = "truck", target = "truck")
    @Mapping(source = "arrivalWindow", target = "arrivalWindow")
    @Mapping(source = "sellerId", target = "sellerId")
    @Mapping(source = "materialType", target = "materialType")
    @Mapping(source = "slot", target = "slot")
    AppointmentJpaEntity domainToEntity(Appointment appointment);
}
