package kfoodbox.food.repository;

import kfoodbox.food.dto.request.FoodsCondition;
import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.entity.Food;
import kfoodbox.food.entity.FoodCategory;
import kfoodbox.food.entity.FoodImage;
import kfoodbox.food.entity.KoreaRegion;
import kfoodbox.food.entity.KoreaRestaurant;
import kfoodbox.food.entity.KoreaRestaurantTag;
import kfoodbox.food.entity.RestaurantCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FoodRepository {
    FoodResponse findFoodById(@Param("id") Long id);
    Food findFoodEntityById(@Param("id") Long id);
    List<Food> findAllFoodEntities();
    Food findFoodEntityByLabelId(@Param("labelId") Long labelId);
    List<FoodsResponse.Food> findFoodsByCategoryId(@Param("id") Long id);
    FoodCategoryResponse findFoodCategoryById(@Param("id") Long id);
    FoodCategory findFoodCategoryEntityById(@Param("id") Long id);
    List<AllFoodCategoriesResponse.FoodCategory> findAllFoodCategories();
    Long getTotalCountOfFoodEntitiesByQuery(@Param("condition") FoodsCondition condition);
    List<Food> findFoodEntitiesByQuery(@Param("query") String query);
    List<Food> findFoodEntitiesByCondition(@Param("cursor") Long cursor, @Param("condition") FoodsCondition condition);
    FoodImage findTopFoodImageByFoodId(@Param("foodId") Long foodId);
    KoreaRegion findKoreaRegionById(@Param("id") Long id);
    RestaurantCategory findRestaurantCategoryById(@Param("id") Long id);
    KoreaRestaurantTag findKoreaRestaurantTagByKoreaRegionIdAndRestaurantCategoryId(@Param("koreaRegionId") Long koreaRegionId, @Param("restaurantCategoryId") Long restaurantCategoryId);
    List<KoreaRestaurant> findKoreaRestaurantsByKoreaRestaurantTagId(@Param("koreaRestaurantTagId") Long koreaRestaurantTagId);
}
