package kfoodbox.article.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CommunityArticleCreateRequest {
    @NotBlank @Size(min = 1, max = 200)
    @Schema(description = "제목\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상 200 이하\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String title;

    @NotBlank @Size(min = 1, max = 16777215)
    @Schema(description = "제목\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상 16777215 이하\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String content;

    @NotNull @Size(min = 1, max = 10)
    @Schema(description = "이미지 URL 리스트\n" +
                          "- Not null\n" +
                          "- 리스트의 길이가 1 이상 10 이하")
    private List<String> imageUrls;
}
