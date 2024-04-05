package kfoodbox.article.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class CommunityArticle {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Boolean isNotice;
    private Date createdAt;
    private Date updatedAt;
}
