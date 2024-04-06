package kfoodbox.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter @Builder
public class UnprocessableEntityExceptionResponse {
    @Schema(description = "제약조건 위반 property별 메시지 map")
    private Map<String, String> messages;
    @Schema(description = "에러 코드")
    private String code;
}
