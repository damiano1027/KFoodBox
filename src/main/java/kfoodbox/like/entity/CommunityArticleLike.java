package kfoodbox.like.entity;

import lombok.Getter;

@Getter
public class CommunityArticleLike {
    private Long id;
    private Long communityArticleId;
    private Long userId;
}
