package be.kdg.prog6.landsideContext.testcontainers;

import be.kdg.prog6.landsideContext.adapters.out.db.*;
import be.kdg.prog6.landsideContext.adapters.out.amqp.AppointmentScheduledAMQPPublisher;
import be.kdg.prog6.landsideContext.domain.*;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.in.ScheduleAppointmentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
class ScheduleAppointmentUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    @Autowired
    private TruckJpaRepository truckJpaRepository;

    @Autowired
    private AppointmentJpaRepository appointmentJpaRepository;

    @MockBean
    private AppointmentScheduledAMQPPublisher appointmentScheduledPort;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        appointmentJpaRepository.deleteAll();
        truckJpaRepository.deleteAll();
        
        // Mock the event publisher to do nothing
        doNothing().when(appointmentScheduledPort).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_IntegrationTest_WithNewTruck() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String sellerName = "Minerals & Co. Ltd";
        String licensePlate = "TRK-001";
        String rawMaterialName = "Gypsum";
        LocalDateTime scheduledTime = LocalDateTime.now().plusHours(2);

        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                sellerId, 
                sellerName,
                new Truck(UUID.randomUUID(), new LicensePlate(licensePlate), Truck.TruckType.LARGE),
                rawMaterialName, 
                scheduledTime
        );

        // Act
        Appointment appointmentId = scheduleAppointmentUseCase.scheduleAppointment(command);

        // Assert
        assertNotNull(appointmentId);
        
        // Verify truck was created and saved
        Optional<TruckJpaEntity> savedTruck = truckJpaRepository.findByLicensePlate(licensePlate);
        assertTrue(savedTruck.isPresent());
        assertEquals(licensePlate, savedTruck.get().getLicensePlate());
        assertEquals("LARGE", savedTruck.get().getTruckType().name());
        assertEquals(25.0, savedTruck.get().getCapacityInTons());

        // Verify appointment was created and saved
        Optional<AppointmentJpaEntity> savedAppointment = appointmentJpaRepository.findById(appointmentId);
        assertTrue(savedAppointment.isPresent());
        assertEquals(sellerId, savedAppointment.get().getSellerId());
        assertEquals(sellerName, savedAppointment.get().getSellerName());
        assertEquals(rawMaterialName, savedAppointment.get().getRawMaterialName());
        assertEquals("SCHEDULED", savedAppointment.get().getStatus().name());
        assertEquals(scheduledTime, savedAppointment.get().getScheduledTime());
        
        // Verify appointment is linked to the truck
        assertNotNull(savedAppointment.get().getTruck());
        assertEquals(licensePlate, savedAppointment.get().getTruck().getLicensePlate());

        // Verify event was published
        verify(appointmentScheduledPort, times(1)).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_IntegrationTest_WithExistingTruck() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String sellerName = "Minerals & Co. Ltd";
        String licensePlate = "TRK-002";
        String rawMaterialName = "Iron_Ore";
        LocalDateTime scheduledTime = LocalDateTime.now().plusHours(3);

        // First, create and save a truck
        TruckJpaEntity existingTruck = new TruckJpaEntity();
        existingTruck.setTruckId(UUID.randomUUID());
        existingTruck.setLicensePlate(licensePlate);
        existingTruck.setTruckType(TruckJpaEntity.TruckType.MEDIUM);
        existingTruck.setCapacityInTons(10.0);
        truckJpaRepository.save(existingTruck);

        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                sellerId, 
                sellerName,
                new Truck(UUID.randomUUID(), new LicensePlate(licensePlate), Truck.TruckType.MEDIUM),
                rawMaterialName, 
                scheduledTime
        );

        // Act
        Appointment appointmentId = scheduleAppointmentUseCase.scheduleAppointment(command);

        // Assert
        assertNotNull(appointmentId);
        
        // Verify no new truck was created (should reuse existing one)
        long truckCount = truckJpaRepository.count();
        assertEquals(1, truckCount);

        // Verify appointment was created and linked to existing truck
        Optional<AppointmentJpaEntity> savedAppointment = appointmentJpaRepository.findById(appointmentId);
        assertTrue(savedAppointment.isPresent());
        assertEquals(existingTruck.getTruckId(), savedAppointment.get().getTruck().getTruckId());
        assertEquals(licensePlate, savedAppointment.get().getTruck().getLicensePlate());

        // Verify event was published
        verify(appointmentScheduledPort, times(1)).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_IntegrationTest_ValidationFailure() {
        // Arrange - Invalid command with null seller ID
        ScheduleAppointmentCommand invalidCommand = new ScheduleAppointmentCommand(
                null, // Invalid: null seller ID
                "Test Seller",
                new Truck(UUID.randomUUID(), new LicensePlate("TRK-003"), Truck.TruckType.SMALL),
                "Gypsum",
                LocalDateTime.now().plusHours(1)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(invalidCommand)
        );
        
        assertEquals("Seller ID is required", exception.getMessage());
        
        // Verify no data was persisted
        assertEquals(0, truckJpaRepository.count());
        assertEquals(0, appointmentJpaRepository.count());
        
        // Verify no event was published
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }
}
