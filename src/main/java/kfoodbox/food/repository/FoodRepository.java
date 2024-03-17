package kfoodbox.food.repository;

import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FoodRepository {
    FoodResponse findFoodById(@Param("id") Long id);
    List<FoodsResponse.Food> findFoodsByCategoryId(@Param("id") Long id);
    FoodCategoryResponse findFoodCategoryById(@Param("id") Long id);
    List<AllFoodCategoriesResponse.FoodCategory> findAllFoodCategories();
}
