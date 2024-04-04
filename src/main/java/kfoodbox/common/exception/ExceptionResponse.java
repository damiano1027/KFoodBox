package kfoodbox.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ExceptionResponse {
    private String message;
    private String code;
}
