package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.adapters.out.db.entities.SlotJpaEntity;
import be.kdg.prog6.landsideContext.adapters.out.db.repositories.SlotJpaRepository;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.ports.in.ScheduleTruckArrivalUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@Service
public class TruckService implements ScheduleTruckArrivalUseCase {
    private final SlotJpaRepository slotJpaRepository;  // Repo to fetch slots

    public TruckService(final SlotJpaRepository slotJpaRepository) {
        this.slotJpaRepository = slotJpaRepository;
    }

    private SlotJpaEntity getSlotForTime(LocalDateTime arrivalTime) {
//        return slotJpaRepository.findSlotByTime(arrivalTime);
        return null;
    }

    @Override
    public void scheduleTruckArrival(Truck truck, LocalDateTime arrivalTime) {
//        SlotJpaEntity slot = getSlotForTime(arrivalTime);
//        slot.bookSlot(truck);
    }
}
