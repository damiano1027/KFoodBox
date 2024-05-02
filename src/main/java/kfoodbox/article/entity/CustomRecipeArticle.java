package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

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

    public boolean hasSameUserId(long userId) {
        return this.userId.equals(userId);
    }

    public boolean isUpdateRequired(CustomRecipeArticleUpdateRequest request) {
        if (Objects.isNull(foodId)) {
            return !Objects.isNull(request.getFoodId())
                    || !title.equals(request.getTitle())
                    || !content.equals(request.getContent());
        } else {
            return !foodId.equals(request.getFoodId())
                    || !title.equals(request.getTitle())
                    || !content.equals(request.getContent());
        }
    }

    public void update(CustomRecipeArticleUpdateRequest request) {
        this.foodId = request.getFoodId();
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
