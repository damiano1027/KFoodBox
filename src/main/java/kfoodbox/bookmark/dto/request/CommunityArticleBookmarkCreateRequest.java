package kfoodbox.bookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommunityArticleBookmarkCreateRequest {
    @NotNull
    @Schema(description = "자유게시판 게시물 id\n" +
                          "- Not null")
    private Long communityArticleId;
}
