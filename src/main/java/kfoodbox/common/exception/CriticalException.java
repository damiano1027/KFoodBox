package kfoodbox.common.exception;

import lombok.Getter;

@Getter
public class CriticalException extends CustomException {
    public CriticalException(ExceptionInformation exceptionInformation) {
        super(exceptionInformation.getMessage(), exceptionInformation.name(), exceptionInformation.getHttpStatus());
    }
}
