package kfoodbox.common.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter @Builder
public class RequestedDataExceptionResponse {
    private Map<String, String> messages;
    private String code;
}
