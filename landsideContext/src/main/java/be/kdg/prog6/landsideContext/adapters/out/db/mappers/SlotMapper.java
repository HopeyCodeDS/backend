package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.domain.Slot;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SlotMapper {

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    SlotJpaEntity domainToEntity(Slot slot);

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    Slot entityToDomain(SlotJpaEntity slotJpaEntity);
}
