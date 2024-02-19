package kfoodbox.bookmark.entity;

import lombok.Getter;

@Getter
public class CommunityArticleBookmark {
    private Long id;
    private Long userId;
    private Long communityArticleId;
}
