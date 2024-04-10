package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.request.CommunityCommentUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class CommunityComment {
    private Long id;
    private Long communityArticleId;
    private Long userId;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public static CommunityComment from(CommunityCommentCreateRequest request) {
        return CommunityComment.builder()
                .content(request.getContent())
                .build();
    }

    public void changeCommunityArticleId(long communityArticleId) {
        this.communityArticleId = communityArticleId;
    }

    public void changeUserId(long userId) {
        this.userId = userId;
    }

    public boolean hasSameUserId(long userId) {
        return this.userId.equals(userId);
    }

    public boolean isUpdateRequired(CommunityCommentUpdateRequest request) {
        return !content.equals(request.getContent());
    }

    public void update(CommunityCommentUpdateRequest request) {
        content = request.getContent();
    }
}
