package kfoodbox.common.exception;

public class NonCriticalException extends CustomException {
    public NonCriticalException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation.getMessage(), exceptionInformation.name(), exceptionInformation.getHttpStatus());
    }
}
