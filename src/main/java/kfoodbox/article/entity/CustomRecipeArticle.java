package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class CustomRecipeArticle {
    private Long id;
    private Long foodId;
    private Long userId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public static CustomRecipeArticle from(CustomRecipeArticleCreateRequest request) {
        return CustomRecipeArticle.builder()
                .foodId(request.getFoodId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public void changeUserId(long userId) {
        this.userId = userId;
    }
}
