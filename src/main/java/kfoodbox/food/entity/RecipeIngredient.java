package kfoodbox.food.entity;

import lombok.Getter;

@Getter
public class RecipeIngredient {
    private Long id;
    private Long foodId;
    private String name;
    private String quantity;
}
