package kfoodbox.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ExceptionResponse {
    @Schema(description = "에러 메시지")
    private String message;
    @Schema(description = "에러 코드")
    private String code;
}
