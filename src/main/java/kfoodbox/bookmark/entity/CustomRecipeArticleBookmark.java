package kfoodbox.bookmark.entity;

import kfoodbox.bookmark.dto.request.CustomRecipeArticleBookmarkCreateRequest;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CustomRecipeArticleBookmark {
    private Long id;
    private Long userId;
    private Long customRecipeArticleId;

    public static CustomRecipeArticleBookmark from(CustomRecipeArticleBookmarkCreateRequest request) {
        return CustomRecipeArticleBookmark.builder()
                .customRecipeArticleId(request.getCustomRecipeArticleId())
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
