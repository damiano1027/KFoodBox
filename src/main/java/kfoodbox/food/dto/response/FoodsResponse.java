package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FoodsResponse {
    @Schema(description = "음식 리스트")
    private List<Food> foods;

    @Getter
    public static class Food {
        @Schema(description = "음식 id")
        private Long id;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "영어 이름")
        private String englishName;
    }
}
