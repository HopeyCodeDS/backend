package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.AppointmentJpaEntity;
import be.kdg.prog6.landsideContext.domain.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {TruckMapper.class, SlotMapper.class}, componentModel = "spring")
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
