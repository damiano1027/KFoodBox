package kfoodbox.article.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class CustomRecipeArticle {
    private Long id;
    private Long foodId;
    private Long userId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
