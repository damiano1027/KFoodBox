package kfoodbox.food.service;

import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public FoodsResponse findFoodsInCategory(Long id) {
        List<FoodsResponse.Food> foods = foodRepository.findFoodsByCategoryId(id);
        return new FoodsResponse(foods);
    }

    @Override
    public AllFoodCategoriesResponse findAllFoodCategories() {
        List<AllFoodCategoriesResponse.FoodCategory> foodCategories = foodRepository.findAllFoodCategories();
        return new AllFoodCategoriesResponse(foodCategories);
    }
}
