package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeCommentCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class CustomRecipeComment {
    private Long id;
    private Long customRecipeArticleId;
    private Long userId;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public static CustomRecipeComment from(CustomRecipeCommentCreateRequest request) {
        return CustomRecipeComment.builder()
                .content(request.getContent())
                .build();
    }

    public void changeCustomRecipeArticleId(long customRecipeArticleId) {
        this.customRecipeArticleId = customRecipeArticleId;
    }

    public void changeUserId(long userId) {
        this.userId = userId;
    }
}
