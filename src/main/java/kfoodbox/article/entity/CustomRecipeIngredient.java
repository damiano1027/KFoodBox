package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class CustomRecipeIngredient {
    private Long id;
    private Long customRecipeArticleId;
    private String name;
    private String quantity;

    public static List<CustomRecipeIngredient> from(long customRecipeArticleId, CustomRecipeArticleCreateRequest request) {
        return request.getIngredients().stream()
                .map(ingredient -> CustomRecipeIngredient.builder()
                        .customRecipeArticleId(customRecipeArticleId)
                        .name(ingredient.getName())
                        .quantity(ingredient.getQuantity())
                        .build()
                ).collect(Collectors.toList());
    }
}
