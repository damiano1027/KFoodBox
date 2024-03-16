package kfoodbox.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllFoodCategoriesResponse {
    private List<FoodCategory> foodCategories;

    @Getter
    public static class FoodCategory {
        private Long id;
        private String name;
        private String imageUrl;
    }
}
