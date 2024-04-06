package kfoodbox.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter @Builder
public class UnprocessableEntityExceptionResponse {
    private Map<String, String> messages;
    private String code;
}
