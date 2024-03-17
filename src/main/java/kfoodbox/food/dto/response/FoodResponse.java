package kfoodbox.food.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FoodResponse {
    private Long id;
    private String name;
    private String englishName;
    private List<String> imageUrls;
    private String explanation;
    private String englishExplanation;
    private String explanationSource;
    private String recipeSource;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeSequence> recipeSequence;

    @Getter
    private static class RecipeIngredient {
        private String name;
        private String quantity;
    }

    @Getter
    private static class RecipeSequence {
        private Long sequenceNumber;
        private String content;
    }
}
