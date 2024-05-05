package kfoodbox.search.dto.response;

import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class IntegratedSearchResponse {
    private FoodInformation food;
    private CommunityInformation community;
    private CustomRecipeInformation customRecipe;

    @Getter @Builder
    public static class FoodInformation {
        private Long foodId;
        private String foodName;
        private Integer totalResultCount;
    }

    @Getter @Builder
    public static class CommunityInformation {
        private Long articleId;
        private String articleTitle;
        private String articleContent;
        private Integer totalResultCount;
    }

    @Getter @Builder
    public static class CustomRecipeInformation {
        private Long articleId;
        private String articleTitle;
        private String articleContent;
        private Integer totalResultCount;
    }

    public static IntegratedSearchResponse of(List<Food> foods, List<CommunityArticle> communityArticles, List<CustomRecipeArticle> customRecipeArticles) {
        FoodInformation foodInformation = null;
        CommunityInformation communityInformation = null;
        CustomRecipeInformation customRecipeInformation = null;

        if (!foods.isEmpty()) {
            Food topFood = foods.get(0);
            foodInformation = FoodInformation.builder()
                    .foodId(topFood.getId())
                    .foodName(topFood.getName())
                    .totalResultCount(foods.size())
                    .build();
        }
        if (!communityArticles.isEmpty()) {
            CommunityArticle topCommunityArticle = communityArticles.get(0);
            communityInformation = CommunityInformation.builder()
                    .articleId(topCommunityArticle.getId())
                    .articleTitle(topCommunityArticle.getTitle())
                    .articleContent(topCommunityArticle.getContent())
                    .totalResultCount(communityArticles.size())
                    .build();
        }
        if (!customRecipeArticles.isEmpty()) {
            CustomRecipeArticle topCustomRecipeArticle = customRecipeArticles.get(0);
            customRecipeInformation = CustomRecipeInformation.builder()
                    .articleId(topCustomRecipeArticle.getId())
                    .articleTitle(topCustomRecipeArticle.getTitle())
                    .articleContent(topCustomRecipeArticle.getContent())
                    .totalResultCount(customRecipeArticles.size())
                    .build();
        }

        return IntegratedSearchResponse.builder()
                .food(foodInformation)
                .community(communityInformation)
                .customRecipe(customRecipeInformation)
                .build();
    }
}
