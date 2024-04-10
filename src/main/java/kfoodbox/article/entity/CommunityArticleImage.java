package kfoodbox.article.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<String> getUrlSet(List<CommunityArticleImage> communityArticleImages) {
        return communityArticleImages.stream()
                .map(CommunityArticleImage::getUrl)
                .collect(Collectors.toSet());
    }

    public static List<CommunityArticleImage> getList(Long communityArticleId, Set<String> urls) {
        return urls.stream().map(url -> CommunityArticleImage.builder()
                .communityArticleId(communityArticleId)
                .url(url)
                .build()).collect(Collectors.toList()
        );
    }
}
