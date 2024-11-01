package be.kdg.prog6.landsideContext.adapters.out.db.mappers;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.domain.Slot;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SlotMapper {


    SlotJpaEntity domainToEntity(Slot slot);
    @InheritInverseConfiguration
    Slot entityToDomain(SlotJpaEntity slotJpaEntity);
}
