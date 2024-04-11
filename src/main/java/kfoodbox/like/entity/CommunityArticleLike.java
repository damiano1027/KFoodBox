package kfoodbox.like.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommunityArticleLike {
    private Long id;
    private Long communityArticleId;
    private Long userId;
}
