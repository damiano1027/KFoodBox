package kfoodbox.food.dto.response;

import kfoodbox.food.entity.FoodImage;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @Builder
public class RecommendedFoodsResponse {
    private List<Food> foods;

    @Getter @Builder
    public static class Food {
        private Long id;
        private String name;
        private String englishName;
        private String imageUrl;
    }

    public static RecommendedFoodsResponse from(List<kfoodbox.food.entity.Food> foodEntities, List<FoodImage> foodImageEntities) {
        List<Food> foods = new ArrayList<>();

        for (int i = 0; i < foodEntities.size(); i++) {
            kfoodbox.food.entity.Food foodEntity = foodEntities.get(i);
            FoodImage foodImageEntity = foodImageEntities.get(i);

            foods.add(
                    Food.builder()
                            .id(foodEntity.getId())
                            .name(foodEntity.getName())
                            .englishName(foodEntity.getEnglishName())
                            .imageUrl(foodImageEntity == null ? null : foodImageEntity.getUrl())
                            .build()
            );
        }

        return RecommendedFoodsResponse.builder()
                .foods(foods)
                .build();
    }

    public static RecommendedFoodsResponse empty() {
        return RecommendedFoodsResponse.builder()
                .foods(new ArrayList<>())
                .build();
    }
}
