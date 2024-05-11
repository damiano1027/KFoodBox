package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FoodCategoryResponse {
    @Schema(description = "음식 카테고리 id")
    private Long id;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "영어 이름")
    private String englishName;
    @Schema(description = "설명")
    private String explanation;
}
