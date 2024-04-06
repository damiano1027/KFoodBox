package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllFoodCategoriesResponse {
    @Schema(description = "음식 카테고리 리스트")
    private List<FoodCategory> foodCategories;

    @Getter
    public static class FoodCategory {
        @Schema(description = "음식 카테고리 id")
        private Long id;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "이미지 URL")
        private String imageUrl;
    }
}
