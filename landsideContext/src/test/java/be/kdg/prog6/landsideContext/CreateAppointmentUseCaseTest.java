//package be.kdg.prog6.landsideContext;
//
//import be.kdg.prog6.common.domain.MaterialType;
//import be.kdg.prog6.landsideContext.domain.Truck;
//import be.kdg.prog6.landsideContext.ports.in.CreateAppointmentCommand;
//import be.kdg.prog6.landsideContext.core.CreateAppointmentUseCaseImpl;
//import be.kdg.prog6.landsideContext.domain.Appointment;
//import be.kdg.prog6.landsideContext.domain.Calendar;
//import be.kdg.prog6.landsideContext.domain.Slot;
//import be.kdg.prog6.landsideContext.ports.out.AppointmentRepositoryPort;
//import be.kdg.prog6.landsideContext.ports.out.CalendarRepositoryPort;
//import be.kdg.prog6.landsideContext.ports.out.SlotRepositoryPort;
//import be.kdg.prog6.landsideContext.ports.out.TruckRepositoryPort;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CreateAppointmentUseCaseTest {
//
//    private CreateAppointmentUseCaseImpl createAppointmentUseCase;
//    private AppointmentRepositoryPort appointmentRepository;
//    private TruckRepositoryPort truckRepository;
//    private CalendarRepositoryPort calendarRepository;
//    private SlotRepositoryPort slotRepository;
//    private Calendar calendar; // Declare calendar as a class variable
//
//
//    @BeforeEach
//    void setUp() {
//        appointmentRepository = mock(AppointmentRepositoryPort.class);
//        calendarRepository = mock(CalendarRepositoryPort.class);
//        createAppointmentUseCase = new CreateAppointmentUseCaseImpl(calendarRepository, appointmentRepository,truckRepository, slotRepository);
//
//        // Initialize a mock Calendar object to be reused
//        calendar = mock(Calendar.class);
//        when(calendarRepository.getCalendar()).thenReturn(calendar);
//    }
//
//    @Test
//    void createAppointment_Success_UsesAvailableSlot() {
//        // Arrange
//        UUID sellerId = UUID.randomUUID();
//        String licensePlate = "ABC-123";
//        MaterialType material = MaterialType.IRON_ORE; // Directly assign material type
//        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
//        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, material, arrivalWindow);
//
//        Truck expectedTruck = new Truck(licensePlate, material);
//
//        // Mocking the calendar to return an available slot
//        Slot availableSlot = new Slot(arrivalWindow, arrivalWindow.plusHours(1));
//        when(calendar.findAvailableSlot(arrivalWindow)).thenReturn(Optional.of(availableSlot));
//
//        // Act
//        Appointment result = createAppointmentUseCase.createAppointment(command);
//
//        // Assert
//        assertNotNull(result, "The appointment should not be null.");
//        assertEquals(licensePlate, result.getTruck().getLicensePlate(), "The license plate should match.");
//        assertEquals(arrivalWindow, result.getArrivalWindow(), "The arrival window should match.");
//        verify(appointmentRepository, times(1)).save(result); // Verifying save operation
//        verify(calendar, times(1)).bookSlot(availableSlot, result.getTruck()); // Verifying slot booking
//    }
//
//    @Test
//    void createAppointment_NoAvailableSlot_CreatesNewSlot() {
//        // Arrange
//        UUID sellerId = UUID.randomUUID();
//        String licensePlate = "ABC-123";
//        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
//        MaterialType material = MaterialType.IRON_ORE; // Directly assign material type
//        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, material, arrivalWindow);
//
//        Truck expectedTruck = new Truck(licensePlate, material);
//
//        // Mocking the calendar to return no available slot
//        when(calendar.findAvailableSlot(arrivalWindow)).thenReturn(Optional.empty());
//
//        // Act
//        Appointment result = createAppointmentUseCase.createAppointment(command);
//
//        // Assert
//        assertNotNull(result, "The appointment should not be null.");
//        assertEquals(licensePlate, result.getTruck().getLicensePlate(), "The license plate should match.");
//
//        // Verifying save operation
//        verify(appointmentRepository, times(1)).save(result);
//
//        // Verifying a new slot was created and booked
//        ArgumentCaptor<Slot> slotCaptor = ArgumentCaptor.forClass(Slot.class);
//        verify(calendar, times(1)).bookSlot(slotCaptor.capture(), eq(result.getTruck()));
//
//        // Verify that the newly created slot has the correct start and end times
//        Slot createdSlot = slotCaptor.getValue();
//        assertEquals(arrivalWindow, createdSlot.getStartTime(), "The slot start time should match the arrival window.");
//        assertEquals(arrivalWindow.plusHours(1), createdSlot.getEndTime(), "The slot end time should be one hour after the start time.");
//    }
//
//    @Test
//    void createAppointment_NullMaterialType_ThrowsException() {
//        // Arrange
//        UUID sellerId = UUID.randomUUID();
//        String licensePlate = "ABC-123";
//        LocalDateTime arrivalWindow = LocalDateTime.now().plusDays(1);
//        CreateAppointmentCommand command = new CreateAppointmentCommand(sellerId, licensePlate, null, arrivalWindow);
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> createAppointmentUseCase.createAppointment(command),
//                "Expected an IllegalArgumentException to be thrown for null material type.");
//    }
//}
