package be.kdg.prog6.warehousingContext.core;


import be.kdg.prog6.common.events.WarehouseActivityEvent;
import be.kdg.prog6.warehousingContext.domain.*;
import be.kdg.prog6.warehousingContext.domain.commands.DeliverPayloadCommand;
import be.kdg.prog6.warehousingContext.ports.in.ProjectWarehouseActivityUseCase;
import be.kdg.prog6.warehousingContext.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@Import({
        DeliverPayloadUseCaseImpl.class,
        ConveyorBeltAssignmentService.class
})
@ActiveProfiles("test")
public class DefaultPayloadDeliveryUseCaseImplIntegrationTest {
    @Autowired
    private DeliverPayloadUseCaseImpl deliverPayloadUseCase;

    @Autowired
    private ConveyorBeltAssignmentService conveyorBeltAssignmentService;

    @MockBean
    private PDTGeneratedPort pdtGeneratedPort;

    @MockBean
    private WarehouseActivityEventPublisherPort warehouseActivityEventPublisherPort;

    @MockBean
    private ProjectWarehouseActivityUseCase projectWarehouseActivityUseCase;

    @Autowired
    private PDTRepositoryPort pdtRepositoryPort;

    @Autowired
    private WarehouseRepositoryPort warehouseRepositoryPort;

    @Autowired
    private WarehouseActivityRepositoryPort warehouseActivityRepositoryPort;

    private UUID sellerId;
    private String warehouseNumber;
    private String rawMaterialName;

    @BeforeEach
    void setUp() {
        sellerId = UUID.fromString("ef01c728-ce36-46b5-a110-84f53fdd9668");
        warehouseNumber = "Warehouse-01";
        rawMaterialName = "Gypsum";

        // Clearing any existing test data
        pdtRepositoryPort.deleteAll();
        warehouseActivityRepositoryPort.deleteAll();
    }

    @Test
    @Transactional
    void deliverPayload_Integration_Success_WithRealDatabase() {
        // Arrange
        String licensePlate = "TRK-001";
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

        // Mocking external dependencies
        doNothing().when(warehouseActivityEventPublisherPort)
                .publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));
        doNothing().when(projectWarehouseActivityUseCase)
                .projectWarehouseActivity(any(WarehouseActivity.class));
        doNothing().when(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));

        // Act
        PayloadDeliveryTicket result = deliverPayloadUseCase.deliverPayload(command);

        // Assert
        assertNotNull(result);
        assertEquals(licensePlate, result.getLicensePlate());
        assertEquals(rawMaterialName, result.getRawMaterialName());
        assertEquals(warehouseNumber, result.getWarehouseNumber());
        assertEquals("Conveyor-1", result.getConveyorBeltNumber()); // Based on ConveyorBeltAssignmentService
        assertEquals(payloadWeight, result.getPayloadWeight());
        assertEquals(sellerId, result.getSellerId());
        assertEquals(deliveryTime, result.getDeliveryTime());
        assertEquals(newWeighingBridgeNumber, result.getNewWeighingBridgeNumber());

        // Verify PDT was saved to database
        Optional<PayloadDeliveryTicket> savedPdt = pdtRepositoryPort.findById(result.getPdtId());
        assertTrue(savedPdt.isPresent());
        assertEquals(result.getPdtId(), savedPdt.get().getPdtId());

        // Verify warehouse activity was saved to database
        var activities = warehouseActivityRepositoryPort.findByWarehouseId(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440501")); // Warehouse-01 ID from data.sql
        assertFalse(activities.isEmpty());

        // Find the activity for this delivery
        var deliveryActivity = activities.stream()
                .filter(activity -> activity.getLicensePlate().equals(licensePlate))
                .findFirst();
        assertTrue(deliveryActivity.isPresent());
        assertEquals(payloadWeight, deliveryActivity.get().getAmount());
        assertEquals(WarehouseActivityAction.PAYLOAD_DELIVERED, deliveryActivity.get().getAction());

        // Verify external dependencies were called
        verify(warehouseActivityEventPublisherPort).publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));
        verify(projectWarehouseActivityUseCase).projectWarehouseActivity(any(WarehouseActivity.class));
        verify(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));
    }

    @Test
    @Transactional
    void deliverPayload_Integration_UpdatesWarehouseCapacity() {
        // Arrange
        String licensePlate = "TRK-002";
        double payloadWeight = 25.0;
        LocalDateTime deliveryTime = LocalDateTime.now();
        String newWeighingBridgeNumber = "WB-002";

        DeliverPayloadCommand command = new DeliverPayloadCommand(
                licensePlate,
                rawMaterialName,
                warehouseNumber,
                payloadWeight,
                sellerId,
                deliveryTime,
                newWeighingBridgeNumber
        );

        // Mock external dependencies
        doNothing().when(warehouseActivityEventPublisherPort)
                .publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));
        doNothing().when(projectWarehouseActivityUseCase)
                .projectWarehouseActivity(any(WarehouseActivity.class));
        doNothing().when(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));

        // Getting initial warehouse capacity
        var initialActivities = warehouseActivityRepositoryPort.findByWarehouseId(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440501"));
        double initialCapacity = initialActivities.stream()
                .mapToDouble(WarehouseActivity::getAmount)
                .sum();

        // Act
        PayloadDeliveryTicket result = deliverPayloadUseCase.deliverPayload(command);

        // Assert
        assertNotNull(result);

        // Verify capacity was updated
        var updatedActivities = warehouseActivityRepositoryPort.findByWarehouseId(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440501"));
        double updatedCapacity = updatedActivities.stream()
                .mapToDouble(WarehouseActivity::getAmount)
                .sum();

        assertEquals(initialCapacity + payloadWeight, updatedCapacity, 0.01);

        // Verify the specific activity was created
        var newActivity = updatedActivities.stream()
                .filter(activity -> activity.getLicensePlate().equals(licensePlate))
                .findFirst();
        assertTrue(newActivity.isPresent());
        assertEquals(payloadWeight, newActivity.get().getAmount());
        assertEquals(WarehouseActivityAction.PAYLOAD_DELIVERED, newActivity.get().getAction());
    }

    @Test
    @Transactional
    void deliverPayload_Integration_ValidatesWarehouseExists() {
        // Arrange
        String nonExistentWarehouse = "Warehouse-NONEXISTENT";
        DeliverPayloadCommand command = new DeliverPayloadCommand(
                "TRK-003",
                rawMaterialName,
                nonExistentWarehouse,
                25.0,
                sellerId,
                LocalDateTime.now(),
                "WB-003"
        );

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> deliverPayloadUseCase.deliverPayload(command)
        );

        assertEquals("Warehouse not found: " + nonExistentWarehouse, exception.getMessage());

        // Verify no PDT was created
        var allPdts = pdtRepositoryPort.findAll();
        assertTrue(allPdts.isEmpty());

        // Verify no external dependencies were called
        verifyNoInteractions(warehouseActivityEventPublisherPort, projectWarehouseActivityUseCase, pdtGeneratedPort);
    }

    @Test
    @Transactional
    void deliverPayload_Integration_ValidatesMaterialType() {
        // Arrange
        String wrongMaterial = "Iron_Ore"; // Wrong material for Warehouse-01 (which stores Gypsum)
        DeliverPayloadCommand command = new DeliverPayloadCommand(
                "TRK-004",
                wrongMaterial,
                warehouseNumber,
                25.0,
                sellerId,
                LocalDateTime.now(),
                "WB-004"
        );

        // Mock external dependencies
        doNothing().when(warehouseActivityEventPublisherPort)
                .publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deliverPayloadUseCase.deliverPayload(command)
        );

        assertTrue(exception.getMessage().contains("Cannot store") ||
                exception.getMessage().contains("Iron_Ore") ||
                exception.getMessage().contains("Gypsum"));

        // Verify no PDT was created
        var allPdts = pdtRepositoryPort.findAll();
        assertTrue(allPdts.isEmpty());

        // Verify no external dependencies were called
        verifyNoInteractions(projectWarehouseActivityUseCase, pdtGeneratedPort);
    }

    @Test
    @Transactional
    void deliverPayload_Integration_GeneratesUniqueWeighingBridgeNumbers() {
        // Arrange
        String licensePlate1 = "TRK-005";
        String licensePlate2 = "TRK-006";
        double payloadWeight = 25.0;
        LocalDateTime deliveryTime = LocalDateTime.now();

        DeliverPayloadCommand command1 = new DeliverPayloadCommand(
                licensePlate1,
                rawMaterialName,
                warehouseNumber,
                payloadWeight,
                sellerId,
                deliveryTime,
                "WB-005"
        );

        DeliverPayloadCommand command2 = new DeliverPayloadCommand(
                licensePlate2,
                rawMaterialName,
                warehouseNumber,
                payloadWeight,
                sellerId,
                deliveryTime,
                "WB-006"
        );

        // Mock external dependencies
        doNothing().when(warehouseActivityEventPublisherPort)
                .publishWarehouseActivityEvent(any(WarehouseActivityEvent.class));
        doNothing().when(projectWarehouseActivityUseCase)
                .projectWarehouseActivity(any(WarehouseActivity.class));
        doNothing().when(pdtGeneratedPort).pdtGenerated(any(PayloadDeliveryTicket.class));

        // Act
        PayloadDeliveryTicket result1 = deliverPayloadUseCase.deliverPayload(command1);
        PayloadDeliveryTicket result2 = deliverPayloadUseCase.deliverPayload(command2);

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.getPdtId(), result2.getPdtId());

        // Verify both PDTs were saved with different IDs
        var savedPdts = pdtRepositoryPort.findAll();
        assertEquals(2, savedPdts.size());

        var pdtIds = savedPdts.stream().map(PayloadDeliveryTicket::getPdtId).toList();
        assertTrue(pdtIds.contains(result1.getPdtId()));
        assertTrue(pdtIds.contains(result2.getPdtId()));
    }
}
