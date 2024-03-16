package kfoodbox.food.service;

import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodsResponse;

public interface FoodService {
    FoodsResponse findFoodsInCategory(Long id);
    AllFoodCategoriesResponse findAllFoodCategories();
}
