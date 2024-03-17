package kfoodbox.food.service;

import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;

public interface FoodService {
    FoodResponse findFood(Long id);
    FoodsResponse findFoodsInCategory(Long id);
    FoodCategoryResponse findFoodCategory(Long id);
    AllFoodCategoriesResponse findAllFoodCategories();
}
