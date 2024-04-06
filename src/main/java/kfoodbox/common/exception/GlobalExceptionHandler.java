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
                            UnprocessableEntityExceptionResponse.builder()
                                    .messages(messages)
                                    .code(ExceptionInformation.UNPROCESSABLE_ENTITY.name())
                                    .build()
                    );
        } else {
            // TODO: 자세한 에러 내용을 개발자가 알 수 있도록 조치
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ExceptionResponse.builder()
                                    .message(ExceptionInformation.INTERNAL_SERVER_ERROR.getMessage())
                                    .code(ExceptionInformation.INTERNAL_SERVER_ERROR.name())
                                    .build()
                    );
        }
    }
}

