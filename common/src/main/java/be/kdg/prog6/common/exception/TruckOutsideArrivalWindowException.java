package be.kdg.prog6.common.exception;

public class TruckOutsideArrivalWindowException extends RuntimeException {
    public TruckOutsideArrivalWindowException(String message) {
        super(message);
    }

    public TruckOutsideArrivalWindowException(String message, Throwable cause) {
        super(message, cause);
    }
}
