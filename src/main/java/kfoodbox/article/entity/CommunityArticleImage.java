package kfoodbox.article.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommunityArticleImage {
    private Long id;
    private Long communityArticleId;
    private String url;

    public static CommunityArticleImage from(Long communityArticleId, String url) {
        return CommunityArticleImage.builder()
                .communityArticleId(communityArticleId)
                .url(url)
                .build();
    }
}
