package Exceptions;

public class NotSupportedOperation extends RuntimeException {
    public NotSupportedOperation(String message) {
        super(message);
    }
}