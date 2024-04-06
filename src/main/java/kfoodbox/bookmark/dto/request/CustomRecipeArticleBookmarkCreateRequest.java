package kfoodbox.bookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CustomRecipeArticleBookmarkCreateRequest {
    @NotNull
    @Schema(description = "나만의 레시피 게시판 게시물 id\n" +
                          "- Not null")
    private Long customRecipeArticleId;
}
