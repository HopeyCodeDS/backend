package be.kdg.prog6.landsideContext.ports.in;

import be.kdg.prog6.landsideContext.domain.EnrichedTruckData;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrichTruckDataUseCase {
    Truck createTruckWithEnrichedData(CreateTruckCommand command);
    EnrichedTruckData enrichTruckData(Truck truck);
    List<EnrichedTruckData> getAllTrucksWithEnrichedData();
    Optional<EnrichedTruckData> getTruckByIdWithEnrichedData(UUID truckId);
    Truck updateTruckWithEnrichedData(UUID truckId, UpdateTruckCommand command);
}