package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.adapters.in.web.dto.TruckOperationResponseDto;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.EnrichedTruckData;
import be.kdg.prog6.landsideContext.domain.Truck;
import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.WeighbridgeTicket;
import be.kdg.prog6.landsideContext.domain.commands.CreateTruckCommand;
import be.kdg.prog6.landsideContext.domain.commands.UpdateTruckCommand;
import be.kdg.prog6.landsideContext.ports.in.*;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.WeighbridgeTicketRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrichTruckDataUseCaseImpl implements EnrichTruckDataUseCase {

    private final GetAllTrucksUseCase getAllTrucksUseCase;
    private final CreateTruckUseCase createTruckUseCase;
    private final UpdateTruckUseCase updateTruckUseCase;
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final TruckMovementRepositoryPort truckMovementRepositoryPort;
    private final WeighbridgeTicketRepositoryPort weighbridgeTicketRepositoryPort;

    @Override
    public List<EnrichedTruckData> getAllTrucksWithEnrichedData() {
        List<Truck> trucks = getAllTrucksUseCase.getAllTrucks();
        return trucks.stream()
                .map(this::enrichTruckData)
                .toList();
    }

    @Override
    public Optional<EnrichedTruckData> getTruckByIdWithEnrichedData(UUID truckId) {
        Optional<Truck> truckOpt = getAllTrucksUseCase.getTruckById(truckId);
        return  truckOpt.map(this::enrichTruckData);
    }

    @Override
    public Truck createTruckWithEnrichedData(CreateTruckCommand command) {
         return createTruckUseCase.createTruck(command);
        
    }

    @Override
    public Truck updateTruckWithEnrichedData(UUID truckId, UpdateTruckCommand command) {
        return updateTruckUseCase.updateTruck(truckId, command);
    }

    @Override
    public EnrichedTruckData enrichTruckData(Truck truck) {
        String licensePlate = truck.getLicensePlate().getValue();

        // Find related appointment
        Optional<Appointment> appointmentOpt = appointmentRepositoryPort.findByLicensePlate(licensePlate)
                .stream()
                .findFirst();

        // Find related truck movement
        Optional<TruckMovement> movementOpt = truckMovementRepositoryPort.findByLicensePlate(licensePlate);

        // Find related weighbridge ticket (most recent)
        Optional<WeighbridgeTicket> ticketOpt = weighbridgeTicketRepositoryPort.findByLicensePlate(licensePlate)
                .stream()
                .findFirst();

        
        // Extract data from appointment
        String material = appointmentOpt.map(app -> app.getRawMaterial().getName()).orElse("Unknown");
        LocalDateTime plannedArrival = appointmentOpt.map(Appointment::getScheduledTime).orElse(null);
        UUID sellerId = appointmentOpt.map(Appointment::getSellerId).orElse(null);
        String sellerName = appointmentOpt.map(Appointment::getSellerName).orElse("Unknown");
        
        // Extract data from truck movement
        LocalDateTime actualArrival = movementOpt.map(TruckMovement::getEntryTime).orElse(null);
        String status = movementOpt.map(movement -> movement.getCurrentLocation().name()).orElse("At the Truck Garage");
        String warehouseNumber = movementOpt.map(TruckMovement::getAssignedWarehouse).orElse("Not assigned or unscheduled");
        
        // Extract weight data from weighbridge ticket
        Double grossWeight = ticketOpt.map(WeighbridgeTicket::getGrossWeight).orElse(null);
        Double tareWeight = ticketOpt.map(WeighbridgeTicket::getTareWeight).orElse(null);
        Double netWeight = ticketOpt.map(WeighbridgeTicket::getNetWeight).orElse(null);

        // Create response DTO directly 
        return new EnrichedTruckData(
            truck.getTruckId(),
            truck.getLicensePlate().getValue(),
            material,
            plannedArrival,
            actualArrival,
            status,
            sellerId,
            sellerName,
            warehouseNumber,
            grossWeight,
            tareWeight,
            netWeight
        );
    }
}