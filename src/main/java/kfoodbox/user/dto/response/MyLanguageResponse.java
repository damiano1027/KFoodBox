package kfoodbox.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyLanguageResponse {
    @Schema(description = "언어 id")
    private Long id;
}
