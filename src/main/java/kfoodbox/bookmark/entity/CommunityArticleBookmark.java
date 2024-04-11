package kfoodbox.bookmark.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommunityArticleBookmark {
    private Long id;
    private Long userId;
    private Long communityArticleId;

    public static CommunityArticleBookmark from(Long communityArticleId) {
        return CommunityArticleBookmark.builder()
                .communityArticleId(communityArticleId)
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
