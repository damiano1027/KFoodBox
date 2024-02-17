package kfoodbox.food.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FoodsResponse {
    private Long foodCategoryId;
    private List<Food> foods;

    @Getter
    private static class Food {
        private Long id;
        private String name;
        private String imageUrl;
    }
}
