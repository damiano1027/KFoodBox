package kfoodbox.food.entity;

import lombok.Getter;

@Getter
public class RecipeSequence {
    private Long id;
    private Long foodId;
    private Long sequenceNumber;
    private String explanation;
}
