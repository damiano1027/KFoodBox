package kfoodbox.article.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommunityCommentUpdateRequest {
    @NotBlank
    @Size(min = 1, max = 65535)
    @Schema(description = "내용\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상 65535 이하\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String content;
}
