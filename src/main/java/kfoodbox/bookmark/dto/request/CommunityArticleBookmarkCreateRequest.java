package kfoodbox.bookmark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommunityArticleBookmarkCreateRequest {
    @NotNull
    private Long communityArticleId;
}
