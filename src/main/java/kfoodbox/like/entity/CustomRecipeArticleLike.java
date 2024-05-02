package kfoodbox.like.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CustomRecipeArticleLike {
    private Long id;
    private Long customRecipeArticleId;
    private Long userId;
}
