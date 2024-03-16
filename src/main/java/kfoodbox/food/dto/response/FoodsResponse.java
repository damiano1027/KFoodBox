package kfoodbox.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FoodsResponse {
    private List<Food> foods;

    @Getter
    public static class Food {
        private Long id;
        private String name;
        private String englishName;
    }
}
