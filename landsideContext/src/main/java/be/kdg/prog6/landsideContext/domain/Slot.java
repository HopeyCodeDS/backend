package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Slot {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<Truck> scheduledTrucks;
    private static final int MAXIMUM_CAPACITY = 40;


    public Slot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduledTrucks = new ArrayList<>();
    }

    public boolean isFull(){
        return scheduledTrucks.size() >= MAXIMUM_CAPACITY;
    }

    public void bookSlot(Truck truck) {
        if (isFull()){
            throw new IllegalStateException("Cannot schedule more than 40 trucks in this time slot.");
        }
        scheduledTrucks.add(truck);
    }

    public void recordTruckArrival(Truck truck, LocalDateTime arrivalTime) {
        truck.setArrivalTime(arrivalTime);
        bookSlot(truck);
    }
}
