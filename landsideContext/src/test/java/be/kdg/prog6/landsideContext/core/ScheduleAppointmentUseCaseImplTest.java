package be.kdg.prog6.landsideContext.core;

import be.kdg.prog6.landsideContext.domain.*;
import be.kdg.prog6.landsideContext.domain.commands.ScheduleAppointmentCommand;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.AppointmentScheduledPort;
import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepositoryPort appointmentRepositoryPort;

    @Mock
    private AppointmentScheduledPort appointmentScheduledPort;

    @Mock
    private TruckRepositoryPort truckRepositoryPort;

    private ScheduleAppointmentUseCaseImpl scheduleAppointmentUseCase;

    @BeforeEach
    void setUp() {
        scheduleAppointmentUseCase = new ScheduleAppointmentUseCaseImpl(
                appointmentRepositoryPort,
                appointmentScheduledPort,
                truckRepositoryPort
        );
    }

    @Test
    void scheduleAppointment_Success_WithNewTruck() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String sellerName = "Minerals & Co. Ltd";
        String licensePlate = "TRK-502";
        String rawMaterialName = "GYPSUM";
        LocalDateTime scheduledTime = LocalDateTime.now().plusHours(2);

        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                sellerId, sellerName,
                new Truck(UUID.randomUUID(), new LicensePlate(licensePlate), Truck.TruckType.LARGE),
                rawMaterialName, scheduledTime
        );

        // Mock truck repository to return no existing truck
        when(truckRepositoryPort.findByLicensePlate(licensePlate))
                .thenReturn(Optional.empty());

        // Mock truck repository save
        doAnswer(invocation -> invocation.getArgument(0))
                .when(truckRepositoryPort).save(any(Truck.class));

        // Mock appointment repository save
        doNothing().when(appointmentRepositoryPort).save(any(Appointment.class));

        // Mock appointment scheduled port
        doNothing().when(appointmentScheduledPort).appointmentScheduled(any(Appointment.class));

        // Act
        Appointment result = scheduleAppointmentUseCase.scheduleAppointment(command);

        // Assert
        assertNotNull(result);
        verify(truckRepositoryPort).findByLicensePlate(licensePlate);
        verify(truckRepositoryPort).save(any(Truck.class));
        verify(appointmentRepositoryPort).save(any(Appointment.class));
        verify(appointmentScheduledPort).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_Success_WithExistingTruck() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String sellerName = "Minerals & Co. Ltd";
        String licensePlate = "TRK-506";
        String rawMaterialName = "IRON_ORE";
        LocalDateTime scheduledTime = LocalDateTime.now().plusHours(2);

        Truck existingTruck = new Truck(
                UUID.randomUUID(),
                new LicensePlate(licensePlate),
                Truck.TruckType.LARGE
        );

        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                sellerId, sellerName, existingTruck, rawMaterialName, scheduledTime
        );

        // Mock truck repository to return existing truck
        when(truckRepositoryPort.findByLicensePlate(licensePlate))
                .thenReturn(Optional.of(existingTruck));

        // Mock appointment repository save
        doNothing().when(appointmentRepositoryPort).save(any(Appointment.class));

        // Mock appointment scheduled port
        doNothing().when(appointmentScheduledPort).appointmentScheduled(any(Appointment.class));

        // Act
        Appointment result = scheduleAppointmentUseCase.scheduleAppointment(command);

        // Assert
        assertNotNull(result);
        verify(truckRepositoryPort).findByLicensePlate(licensePlate);
        verify(truckRepositoryPort, never()).save(any(Truck.class)); // Should not save new truck
        verify(appointmentRepositoryPort).save(any(Appointment.class));
        verify(appointmentScheduledPort).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_NullSellerId() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                null, "Minerals & Co. Ltd",
                new Truck(UUID.randomUUID(), new LicensePlate("TRK-502"), Truck.TruckType.LARGE),
                "GYPSUM", LocalDateTime.now().plusHours(2)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("Seller ID is required", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_NullTruck() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                UUID.randomUUID(), "Minerals & Co. Ltd", null, "GYPSUM", LocalDateTime.now().plusHours(2)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("Truck is required", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_NullLicensePlate() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                UUID.randomUUID(), "Minerals & Co. Ltd",
                new Truck(UUID.randomUUID(), null, Truck.TruckType.LARGE),
                "GYPSUM", LocalDateTime.now().plusHours(2)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("License plate is required", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_NullRawMaterial() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                UUID.randomUUID(), "Minerals & Co. Ltd",
                new Truck(UUID.randomUUID(), new LicensePlate("TRK-502"), Truck.TruckType.LARGE),
                null, LocalDateTime.now().plusHours(2)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("Raw material name is required", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_NullScheduledTime() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                UUID.randomUUID(), "Minerals & Co. Ltd",
                new Truck(UUID.randomUUID(), new LicensePlate("TRK-502"), Truck.TruckType.LARGE),
                "GYPSUM", null
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("Scheduled time is required", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }

    @Test
    void scheduleAppointment_ValidationFailure_ScheduledTimeInPast() {
        // Arrange
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
                UUID.randomUUID(), "Minerals & Co. Ltd",
                new Truck(UUID.randomUUID(), new LicensePlate("TRK-502"), Truck.TruckType.LARGE),
                "GYPSUM", LocalDateTime.now().minusHours(1)
        );

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scheduleAppointmentUseCase.scheduleAppointment(command)
        );

        assertEquals("Scheduled time cannot be in the past", exception.getMessage());
        verify(appointmentRepositoryPort, never()).save(any(Appointment.class));
        verify(appointmentScheduledPort, never()).appointmentScheduled(any(Appointment.class));
    }
}