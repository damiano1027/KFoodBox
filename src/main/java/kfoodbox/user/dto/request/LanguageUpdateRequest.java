package kfoodbox.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LanguageUpdateRequest {
    @NotNull
    @Schema(description = "언어 id\n" +
                          "- Not null")
    private Long languageId;
}
