package kfoodbox.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CriticalException extends CustomException {
    public CriticalException(String message, String code, HttpStatus httpStatus) {
        super(message, code, httpStatus);
    }
}
