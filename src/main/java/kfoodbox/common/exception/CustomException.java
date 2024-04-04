package kfoodbox.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;
    protected CustomException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
