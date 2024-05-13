package kfoodbox.food.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KoreaRestaurantsCondition {
    @NotNull
    private Long koreaRegionId;
    @NotNull
    private Long restaurantCategoryId;
}
