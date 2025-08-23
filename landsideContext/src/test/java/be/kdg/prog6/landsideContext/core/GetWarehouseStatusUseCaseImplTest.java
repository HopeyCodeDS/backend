package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.TruckMovement;
import be.kdg.prog6.landsideContext.domain.TruckLocation;
import be.kdg.prog6.landsideContext.domain.LicensePlate;
import be.kdg.prog6.landsideContext.domain.WarehouseStatus;
import be.kdg.prog6.landsideContext.ports.out.TruckMovementRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetWarehouseStatusUseCaseImplTest {

    @Mock
    private TruckMovementRepositoryPort truckMovementRepositoryPort;

    private GetWarehouseStatusUseCaseImpl getWarehouseStatusUseCase;

    @BeforeEach
    void setUp() {
        getWarehouseStatusUseCase = new GetWarehouseStatusUseCaseImpl(truckMovementRepositoryPort);
    }

    @Test
    void getWarehouseStatus_WhenTruckMovementExists_ShouldReturnCompletedStatus() {
        // Arrange
        String licensePlate = "ABC123";
        String warehouseNumber = "WH001";
        
        TruckMovement mockTruckMovement = createMockTruckMovement(licensePlate, warehouseNumber);
        
        // Stub the repository to return the mock truck movement
        when(truckMovementRepositoryPort.findByLicensePlate(licensePlate))
                .thenReturn(Optional.of(mockTruckMovement));

        // Act
        WarehouseStatus result = getWarehouseStatusUseCase.getWarehouseStatus(licensePlate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo("COMPLETED");
        assertThat(result.message()).isEqualTo("Warehouse assigned successfully");
        assertThat(result.assignedWarehouse()).isEqualTo(warehouseNumber);
    }

    @Test
    void getWarehouseStatus_WhenTruckMovementExistsWithoutWarehouse_ShouldReturnProcessingStatus() {
        // Arrange
        String licensePlate = "XYZ789";
        
        TruckMovement mockTruckMovement = createMockTruckMovement(licensePlate, null);
        
        // Stub the repository to return the mock truck movement
        when(truckMovementRepositoryPort.findByLicensePlate(licensePlate))
                .thenReturn(Optional.of(mockTruckMovement));

        // Act
        WarehouseStatus result = getWarehouseStatusUseCase.getWarehouseStatus(licensePlate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.status()).isEqualTo("PROCESSING");
        assertThat(result.message()).isEqualTo("Warehouse assignment in progress");
        assertThat(result.assignedWarehouse()).isNull();
    }

    @Test
    void getWarehouseStatus_WhenTruckMovementNotFound_ShouldThrowException() {
        // Arrange
        String licensePlate = "NOTFOUND";
        
        // Stub the repository to return empty optional
        when(truckMovementRepositoryPort.findByLicensePlate(licensePlate))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> getWarehouseStatusUseCase.getWarehouseStatus(licensePlate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Truck movement not found");
    }

    @Test
    void getWarehouseStatus_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        String licensePlate = "ERROR123";
        
        // Stub the repository to throw an exception
        when(truckMovementRepositoryPort.findByLicensePlate(licensePlate))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThatThrownBy(() -> getWarehouseStatusUseCase.getWarehouseStatus(licensePlate))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database connection failed");
    }

    /**
     * Helper method to create a mock TruckMovement for testing
     */
    private TruckMovement createMockTruckMovement(String licensePlate, String warehouseNumber) {
        TruckMovement movement = new TruckMovement(
            UUID.randomUUID(),
            new LicensePlate(licensePlate),
            LocalDateTime.now()
        );
        
        // Set the warehouse if provided
        if (warehouseNumber != null) {
            movement.setAssignedWarehouse(warehouseNumber);
        }
        
        return movement;
    }
}