package kfoodbox.bookmark.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CustomRecipeArticleBookmark {
    private Long id;
    private Long userId;
    private Long customRecipeArticleId;

    public static CustomRecipeArticleBookmark from(Long customRecipeArticleId) {
        return CustomRecipeArticleBookmark.builder()
                .customRecipeArticleId(customRecipeArticleId)
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
