package kfoodbox.food.repository;

import kfoodbox.food.dto.response.FoodsResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FoodRepository {
    List<FoodsResponse.Food> findFoodsByCategoryId(@Param("id") Long id);
}
