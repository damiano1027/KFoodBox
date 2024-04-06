package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class FoodResponse {
    @Schema(description = "음식 id")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "영어 이름")
    private String englishName;
    @Schema(description = "이미지 링크 리스트")
    private List<String> imageUrls;
    @Schema(description = "설명")
    private String explanation;
    @Schema(description = "영어 설명")
    private String englishExplanation;
    @Schema(description = "설명 출처")
    private String explanationSource;
    @Schema(description = "레시피 출처")
    private String recipeSource;
    @Schema(description = "레시피 재료 리스트")
    private List<RecipeIngredient> recipeIngredients;
    @Schema(description = "레시피 순서 리스트")
    private List<RecipeSequence> recipeSequence;

    @Getter
    private static class RecipeIngredient {
        @Schema(description = "재료 이름")
        private String name;
        @Schema(description = "양")
        private String quantity;
    }

    @Getter
    private static class RecipeSequence {
        @Schema(description = "순서 번호")
        private Long sequenceNumber;
        @Schema(description = "내용")
        private String content;
    }
}
