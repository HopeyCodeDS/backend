package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.*;
import be.kdg.prog6.landsideContext.domain.commands.RegisterWeightAndExitBridgeCommand;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.TruckLeftWeighingBridgePort;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StubbingRegisterWeightAndExitBridgeUseCaseImplTest {

    @Mock
    private TruckMovementRepositoryPort truckMovementRepository;

    @Mock
    private TruckLeftWeighingBridgePort truckLeftWeighingBridgePort;

    @Mock
    private AppointmentRepositoryPort appointmentRepository;

    private RegisterWeightAndExitBridgeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterWeightAndExitBridgeUseCaseImpl(
                truckMovementRepository,
                truckLeftWeighingBridgePort,
                appointmentRepository
        );
    }

    @Test
    void registerWeightAndExitBridge_Success() {
        // Arrange
        String licensePlate = "ABC123";
        Double weight = 15.5;
        String rawMaterialName = "GRAIN";
        UUID sellerId = UUID.randomUUID();
        UUID movementId = UUID.randomUUID();
        LocalDateTime entryTime = LocalDateTime.now();

        // Create command
        RegisterWeightAndExitBridgeCommand command = new RegisterWeightAndExitBridgeCommand(
                licensePlate, weight, rawMaterialName
        );

        // Create truck movement at weighing bridge
        LicensePlate licensePlateObj = new LicensePlate(licensePlate);
        TruckMovement truckMovement = new TruckMovement(movementId, licensePlateObj, entryTime);
        truckMovement.setCurrentLocation(TruckLocation.WEIGHING_BRIDGE);
        truckMovement.setAssignedBridgeNumber("BRIDGE-001");

        // Create appointment
        Truck truck = new Truck(UUID.randomUUID(), licensePlateObj, Truck.TruckType.MEDIUM);
        RawMaterial rawMaterial = new RawMaterial(rawMaterialName, 100.0, 5.0);
        ArrivalWindow arrivalWindow = new ArrivalWindow(LocalDateTime.now());
        Appointment appointment = new Appointment(
                UUID.randomUUID(), sellerId, "Test Seller", truck, rawMaterial, arrivalWindow, LocalDateTime.now()
        );

        // Stubbing repository calls
        when(truckMovementRepository.findByLicensePlate(licensePlate))
                .thenReturn(Optional.of(truckMovement));
        doNothing().when(truckMovementRepository).save(any(TruckMovement.class));
        when(appointmentRepository.findByLicensePlate(licensePlate))
                .thenReturn(List.of(appointment));

        // Act
        useCase.registerWeightAndExitBridge(command);

        // Assert
        // Verify truck movement was retrieved
        verify(truckMovementRepository).findByLicensePlate(licensePlate);

        // Verify weight was recorded and truck left weighing bridge
        verify(truckMovementRepository).save(truckMovement);

        // Verify appointment was retrieved
        verify(appointmentRepository).findByLicensePlate(licensePlate);

        // Verify event was published
        verify(truckLeftWeighingBridgePort).truckLeftWeighingBridge(
                eq(truckMovement),
                eq(rawMaterialName),
                eq(sellerId)
        );

        // Verify the truck movement state changes
        assert truckMovement.getTruckWeight().equals(weight);
        assert truckMovement.getCurrentLocation() == TruckLocation.WAREHOUSE;
    }
}