package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @Builder
public class QueriedFoodsResponse {
    @Schema(description = "조건에 해당하는 총 게시물의 개수")
    private Long totalCount;

    @Schema(description = "조건에 해당하는 상점중에 현재 페이지에서 조회된 개수")
    private Integer currentCount;

    @Schema(description = "조건에 해당하는 상점들을 조회할 수 있는 최대 페이지")
    private Long totalPage;

    @Schema(description = "현재 페이지")
    private Long currentPage;

    @Schema(description = "음식 리스트")
    private List<Food> foods;

    @Getter @Builder
    public static class Food {
        @Schema(description = "음식 id")
        private Long id;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "영어 이름")
        private String englishName;
    }

    public static QueriedFoodsResponse from(Long totalCount, Long totalPage, Long currentPage, List<kfoodbox.food.entity.Food> foodEntities) {
        List<Food> foods = new ArrayList<>();
        foodEntities.forEach(foodEntity -> {
            foods.add(
                    Food.builder()
                            .id(foodEntity.getId())
                            .name(foodEntity.getName())
                            .englishName(foodEntity.getEnglishName())
                            .build()
            );
        });

        return QueriedFoodsResponse.builder()
                .totalCount(totalCount)
                .currentCount(foodEntities.size())
                .totalPage(totalPage)
                .currentPage(currentPage)
                .foods(foods)
                .build();
    }
}
