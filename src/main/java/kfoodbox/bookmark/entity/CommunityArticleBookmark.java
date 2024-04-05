package kfoodbox.bookmark.entity;

import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommunityArticleBookmark {
    private Long id;
    private Long userId;
    private Long communityArticleId;

    public static CommunityArticleBookmark from(CommunityArticleBookmarkCreateRequest request) {
        return CommunityArticleBookmark.builder()
                .communityArticleId(request.getCommunityArticleId())
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
