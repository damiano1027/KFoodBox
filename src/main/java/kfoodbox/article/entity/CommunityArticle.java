package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class CommunityArticle {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Boolean isNotice;
    private Date createdAt;
    private Date updatedAt;

    public static CommunityArticle from(CommunityArticleCreateRequest request) {
        return CommunityArticle.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public void changeUserId(long userId) {
        this.userId = userId;
    }

    public void changeToNonNotice() {
        isNotice = false;
    }
}
