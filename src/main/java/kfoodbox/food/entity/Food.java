package kfoodbox.food.entity;

import lombok.Getter;

@Getter
public class Food {
    private Long id;
    private Long foodCategoryId;
    private String name;
    private String englishName;
    private Long labelId;
    private String explanation;
    private String englishExplanation;
    private String explanationSource;
    private String recipeSource;
}
