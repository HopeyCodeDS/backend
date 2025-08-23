package be.kdg.prog6.warehousingContext.core;

import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.warehousingContext.domain.*;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;
import be.kdg.prog6.warehousingContext.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliverPayloadUseCaseImplTest {

    @Mock
    private PDTRepositoryPort pdtRepositoryPort;

    @Mock
    private WarehouseProjection warehouseProjection;

    @Mock
    private WarehouseActivity warehouseActivity;

    @Mock
    private PDTGeneratedPort pdtGeneratedPort;

    @Mock
    private WarehouseRepositoryPort warehouseRepositoryPort;

    @Mock
    private WarehouseActivityRepositoryPort warehouseActivityRepositoryPort;

    @Mock
    private ProjectWarehouseActivityUseCase projectWarehouseActivityUseCase;

    @Mock
    private ConveyorBeltAssignmentService conveyorBeltAssignmentService;

    @Mock
    private WarehouseActivityEventPublisherPort warehouseActivityEventPublisherPort;

    private DeliverPayloadUseCaseImpl deliverPayloadUseCase;

    @BeforeEach
    void setUp() {
        deliverPayloadUseCase = new DeliverPayloadUseCaseImpl(
                pdtRepositoryPort,
                pdtGeneratedPort,
                warehouseRepositoryPort,
                warehouseActivityRepositoryPort,
                projectWarehouseActivityUseCase,
                conveyorBeltAssignmentService,
                warehouseActivityEventPublisherPort
        );
    }

    @Test
    void deliverPayload_Success_ValidCommand() throws InstantiationException, IllegalAccessException {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String licensePlate = "TRK-501";
        String rawMaterialName = "Gypsum";
        String warehouseNumber = "WH-001";
        double payloadWeight = 25.0;
        LocalDateTime deliveryTime = LocalDateTime.now();
        String newWeighingBridgeNumber = "WB-001";

        DeliverPayloadCommand command = new DeliverPayloadCommand(
                licensePlate,
                rawMaterialName,
                warehouseNumber,
                payloadWeight,
                sellerId,
                deliveryTime,
                newWeighingBridgeNumber
        );

        UUID warehouseId = UUID.randomUUID();
        RawMaterial rawMaterial = RawMaterial.fromName(rawMaterialName);
        Warehouse warehouse = new Warehouse(warehouseId, warehouseNumber, sellerId, rawMaterial);
        warehouse.setCurrentCapacity(100.0); // Set some initial capacity

        // Mock warehouse repository calls
        when(warehouseRepositoryPort.findByWarehouseNumber(warehouseNumber))
                .thenReturn(Optional.of(warehouse));
        when(warehouseRepositoryPort.findById(warehouseId))
                .thenReturn(Optional.of(warehouse));
        doAnswer(invocation -> invocation.getArgument(0))
                .when(warehouseRepositoryPort).save(any(Warehouse.class));

        // Mock warehouse activity repository
        when(warehouseActivityRepositoryPort.findByWarehouseId(warehouseId))
                .thenReturn(List.of()); // Empty list for capacity calculation
        doAnswer(invocation -> invocation.getArgument(0))
                .when(warehouseActivityRepositoryPort).save(any(WarehouseActivity.class));

        // Mock conveyor belt assignment
        when(conveyorBeltAssignmentService.assignConveyorBelt(rawMaterialName))
                .thenReturn("Conveyor-1");

        // Mock PDT repository
        PayloadDeliveryTicket expectedPdt = new PayloadDeliveryTicket(
                UUID.randomUUID(),
                licensePlate,
                rawMaterialName,
                warehouseNumber,
                "Conveyor-1",
                payloadWeight,
                sellerId,
                deliveryTime,
                newWeighingBridgeNumber
        );
        when(pdtRepositoryPort.save(any(PayloadDeliveryTicket.class)))
                .thenReturn(expectedPdt);

        // Mock other dependencies
        doNothing().when(warehouseActivityEventPublisherPort)
                .publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));

        when(projectWarehouseActivityUseCase.projectWarehouseActivity(any(WarehouseActivity.class)))
                .thenReturn(warehouseProjection);
                
        doNothing().when(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));

        // Act
        PayloadDeliveryTicket result = deliverPayloadUseCase.deliverPayload(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedPdt.getPdtId(), result.getPdtId());
        assertEquals(licensePlate, result.getLicensePlate());
        assertEquals(rawMaterialName, result.getRawMaterialName());
        assertEquals(warehouseNumber, result.getWarehouseNumber());
        assertEquals("Conveyor-1", result.getConveyorBeltNumber());
        assertEquals(payloadWeight, result.getPayloadWeight());
        assertEquals(sellerId, result.getSellerId());
        assertEquals(deliveryTime, result.getDeliveryTime());
        assertEquals(newWeighingBridgeNumber, result.getNewWeighingBridgeNumber());

        // Verify interactions
        verify(warehouseRepositoryPort).findByWarehouseNumber(warehouseNumber);
        verify(warehouseRepositoryPort).findById(warehouseId);
        verify(warehouseRepositoryPort).save(any(Warehouse.class));
        verify(warehouseActivityRepositoryPort).findByWarehouseId(warehouseId);
        verify(warehouseActivityRepositoryPort).save(any(WarehouseActivity.class));
        verify(conveyorBeltAssignmentService).assignConveyorBelt(rawMaterialName);
        verify(pdtRepositoryPort).save(any(PayloadDeliveryTicket.class));
        verify(warehouseActivityEventPublisherPort).publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));
        verify(projectWarehouseActivityUseCase).projectWarehouseActivity(any(WarehouseActivity.class));
        verify(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));
    }

    @Test
    void deliverPayload_ThrowsException_InvalidLicensePlate() {
        // Arrange
        DeliverPayloadCommand command = new DeliverPayloadCommand(
                null, // Invalid license plate
                "GYPSUM",
                "WH-001",
                25.5,
                UUID.randomUUID(),
                LocalDateTime.now(),
                "WB-001"
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deliverPayloadUseCase.deliverPayload(command)
        );

        assertEquals("License plate is required", exception.getMessage());

        // Verify no interactions with repositories
        verifyNoInteractions(warehouseRepositoryPort, pdtRepositoryPort, warehouseActivityRepositoryPort);
    }

    @Test
    void deliverPayload_ThrowsException_WarehouseNotFound() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        DeliverPayloadCommand command = new DeliverPayloadCommand(
                "TRK-501",
                "GYPSUM",
                "WH-NONEXISTENT",
                25.5,
                sellerId,
                LocalDateTime.now(),
                "WB-001"
        );

        // Mock warehouse repository to return empty
        when(warehouseRepositoryPort.findByWarehouseNumber("WH-NONEXISTENT"))
                .thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> deliverPayloadUseCase.deliverPayload(command)
        );

        assertEquals("Warehouse not found: WH-NONEXISTENT", exception.getMessage());

        // Verify only warehouse lookup was attempted
        verify(warehouseRepositoryPort).findByWarehouseNumber("WH-NONEXISTENT");
        verifyNoMoreInteractions(warehouseRepositoryPort);
        verifyNoInteractions(pdtRepositoryPort, warehouseActivityRepositoryPort);
    }

    @Test
    void deliverPayload_ThrowsException_InsufficientCapacity() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String warehouseNumber = "Warehouse-005";
        double payloadWeight = 600_000.0; // Exceeds 500k capacity

        DeliverPayloadCommand command = new DeliverPayloadCommand(
                "TRK-501",
                "GYPSUM",
                warehouseNumber,
                payloadWeight,
                sellerId,
                LocalDateTime.now(),
                "WB-001"
        );

        UUID warehouseId = UUID.randomUUID();
        RawMaterial rawMaterial = RawMaterial.fromName("GYPSUM");
        Warehouse warehouse = new Warehouse(warehouseId, warehouseNumber, sellerId, rawMaterial);
        warehouse.setCurrentCapacity(450_000.0); // Already near capacity

        // Mock warehouse repository calls
        when(warehouseRepositoryPort.findByWarehouseNumber(warehouseNumber))
                .thenReturn(Optional.of(warehouse));

        // Mock warehouse activity repository with high capacity usage
        WarehouseActivity highCapacityActivity = new WarehouseActivity(
                warehouseId,
                400_000.0, // 400k tons already delivered
                WarehouseActivityAction.PAYLOAD_DELIVERED,
                LocalDateTime.now().minusDays(1),
                "GYPSUM",
                "TRK-OLD",
                "Previous delivery that consumed capacity"
        );
        when(warehouseActivityRepositoryPort.findByWarehouseId(warehouseId))
                .thenReturn(List.of(highCapacityActivity));

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> deliverPayloadUseCase.deliverPayload(command)
        );

        assertTrue(exception.getMessage().contains("insufficient capacity") || 
                  exception.getMessage().contains("capacity"));

        // Verify warehouse lookup was attempted
        verify(warehouseRepositoryPort).findByWarehouseNumber(warehouseNumber);
        verify(warehouseActivityRepositoryPort).findByWarehouseId(warehouseId);
        verifyNoMoreInteractions(warehouseRepositoryPort);
        verifyNoInteractions(pdtRepositoryPort);
    }
}