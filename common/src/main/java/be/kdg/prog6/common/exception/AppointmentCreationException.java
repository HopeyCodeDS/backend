package be.kdg.prog6.common.exception;

public class AppointmentCreationException extends RuntimeException {
    public AppointmentCreationException(String message) {
        super(message);
    }
}