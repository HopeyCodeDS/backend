package be.kdg.prog6.landsideContext.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Slot {
    private int id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<Truck> scheduledTrucks;
    public static final int MAXIMUM_CAPACITY = 40;

    public Slot() {
        this.id = getId();
        this.startTime = getStartTime();
        this.endTime = getEndTime();
        this.scheduledTrucks = new ArrayList<>();
    }

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

//    public void recordTruckArrival(Truck truck, LocalDateTime arrivalTime) {
//        truck.setArrivalTime(arrivalTime);
//        bookSlot(truck);
//    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
