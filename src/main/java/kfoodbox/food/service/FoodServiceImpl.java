package kfoodbox.food.service;

import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.entity.FoodCategory;
import kfoodbox.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    @Transactional(readOnly = true)
    public FoodResponse findFood(Long id) {
        FoodResponse response = foodRepository.findFoodById(id);

        if (response == null) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public FoodsResponse findFoodsInCategory(Long id) {
        if (foodRepository.findFoodCategoryEntityById(id) == null) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD_CATEGORY);
        }

        List<FoodsResponse.Food> foods = foodRepository.findFoodsByCategoryId(id);
        return new FoodsResponse(foods);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodCategoryResponse findFoodCategory(Long id) {
        FoodCategoryResponse response = foodRepository.findFoodCategoryById(id);

        if (response == null) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD_CATEGORY);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AllFoodCategoriesResponse findAllFoodCategories() {
        return new AllFoodCategoriesResponse(foodRepository.findAllFoodCategories());
    }
}
