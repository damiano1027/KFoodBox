package kfoodbox.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버에서 에러가 발생했습니다.";
    private static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    private static final String UNPROCESSABLE_ENTITY_CODE = "UNPROCESSABLE_ENTITY";

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable e, HandlerMethod handlerMethod) {
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;

            return ResponseEntity
                    .status(customException.getHttpStatus())
                    .body(
                            ExceptionResponse.builder()
                                    .message(customException.getMessage())
                                    .code(customException.getCode())
                                    .build()
                    );
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;

            Map<String, String> messages = new HashMap<>();
            for (FieldError fieldError : bindException.getFieldErrors()) {
                messages.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(
                            RequestedDataExceptionResponse.builder()
                                    .messages(messages)
                                    .code(UNPROCESSABLE_ENTITY_CODE)
                                    .build()
                    );
        } else {
            // TODO: 자세한 에러 내용을 개발자가 알 수 있도록 조치
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .message(INTERNAL_SERVER_ERROR_MESSAGE)
                                    .code(INTERNAL_SERVER_ERROR_CODE)
                                    .build()
                    );
        }
    }
}

