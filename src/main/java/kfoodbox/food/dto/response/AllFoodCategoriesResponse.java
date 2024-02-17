package kfoodbox.food.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class AllFoodCategoriesResponse {
    private List<FoodCategory> foodCategories;

    @Getter
    private static class FoodCategory {
        private Long id;
        private String name;
        private String imageUrl;
    }
}
