package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kfoodbox.food.entity.Food;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LabelledFoodResponse {
    @Schema(description = "음식 id")
    private Long id;

    @Schema(description = "라벨 id")
    private Long labelId;

    @Schema(description = "이름")
    private String name;

    public static LabelledFoodResponse from(Food food) {
        return LabelledFoodResponse.builder()
                .id(food.getId())
                .labelId(food.getLabelId())
                .name(food.getName())
                .build();
    }
}
