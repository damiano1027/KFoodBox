package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class CustomRecipeArticleImage {
    private Long id;
    private Long customRecipeArticleId;
    private String url;

    public static List<CustomRecipeArticleImage> from(long customRecipeArticleId, CustomRecipeArticleCreateRequest request) {
        return request.getImageUrls().stream()
                .map(imageUrl -> CustomRecipeArticleImage.builder()
                        .customRecipeArticleId(customRecipeArticleId)
                        .url(imageUrl)
                        .build()
                ).collect(Collectors.toList());
    }
}
