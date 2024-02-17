package kfoodbox.food.entity;

import lombok.Getter;

@Getter
public class Food {
    private Long id;
    private Long foodCategoryId;
    private String name;
    private Long labelId;
    private String explanation;
    private String explanationSource;
    private String recipeSource;
}
