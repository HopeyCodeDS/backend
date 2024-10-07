package be.kdg.prog6.landsideContext;

import be.kdg.prog6.common.domain.MaterialType;
import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
import be.kdg.prog6.landsideContext.core.CreateAppointmentUseCaseImpl;
import be.kdg.prog6.landsideContext.domain.Appointment;
import be.kdg.prog6.landsideContext.domain.Calendar;
import be.kdg.prog6.landsideContext.domain.Slot;
import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
import be.kdg.prog6.landsideContext.ports.out.CalendarRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateAppointmentUseCaseTest {

    private CreateAppointmentUseCaseImpl createAppointmentUseCase;
    private AppointmentRepositoryPort appointmentRepository;
    private CalendarRepositoryPort calendarRepository;

    @BeforeEach
    void setUp() {
        appointmentRepository = mock(AppointmentRepositoryPort.class);
        calendarRepository = mock(CalendarRepositoryPort.class);
        createAppointmentUseCase = new CreateAppointmentUseCaseImpl(calendarRepository, appointmentRepository);
    }

    @Test
    void createAppointment_Success_UsesAvailableSlot() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String licensePlate = "ABC-123";
        String materialType = "IRON_ORE";
        MaterialType material = MaterialType.valueOf(materialType);
        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, material, arrivalWindow);

        // Mocking the calendar to return an available slot
        Calendar calendar = mock(Calendar.class);
        Slot availableSlot = new Slot(arrivalWindow, arrivalWindow.plusHours(1));
        when(calendarRepository.getCalendar()).thenReturn(calendar);
        when(calendar.findAvailableSlot(arrivalWindow)).thenReturn(Optional.of(availableSlot));

        // Act
        Appointment result = createAppointmentUseCase.createAppointment(command);

        // Assert
        assertNotNull(result);
        assertEquals(licensePlate, result.getTruck().getLicensePlate());
        assertEquals(arrivalWindow, result.getArrivalWindow());
        verify(appointmentRepository, times(1)).save(result); // Verifying save operation
        verify(calendar, times(1)).bookSlot(availableSlot); // Verifying slot booking
    }

    @Test
    void createAppointment_NoAvailableSlot_CreatesNewSlot() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String licensePlate = "ABC-123";
        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
        String materialType = "IRON_ORE";
        MaterialType material = MaterialType.valueOf(materialType);
        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, material, arrivalWindow);

        // Mocking the calendar to return no available slot
        Calendar calendar = mock(Calendar.class);
        when(calendarRepository.getCalendar()).thenReturn(calendar);
        when(calendar.findAvailableSlot(arrivalWindow)).thenReturn(Optional.empty());

        // Act
        Appointment result = createAppointmentUseCase.createAppointment(command);

        // Assert
        assertNotNull(result);
        assertEquals(licensePlate, result.getTruck().getLicensePlate());
        verify(appointmentRepository, times(1)).save(result); // Verifying save operation
        verify(calendar, times(1)).bookSlot(any(Slot.class)); // Verifying slot creation
    }

    @Test
    void createAppointment_NullMaterialType_ThrowsException() {
        // Arrange
        UUID sellerId = UUID.randomUUID();
        String licensePlate = "ABC-123";
        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, null, arrivalWindow);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createAppointmentUseCase.createAppointment(command));
    }
}
