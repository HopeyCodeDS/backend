package be.kdg.prog6.landsideContext.ports.in;

public interface WeighTruckUseCase {
    String weighTruck(String licensePlate, double weight); // Returns warehouse number after weighing
}
