package kfoodbox.food.service;

import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.food.dto.request.FoodsCondition;
import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.dto.response.LabelledFoodResponse;
import kfoodbox.food.dto.response.QueriedFoodsResponse;
import kfoodbox.food.entity.Food;
import kfoodbox.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    public LabelledFoodResponse findLabelledFood(Long labelId) {
        Food food = foodRepository.findFoodEntityByLabelId(labelId);

        if (Objects.isNull(food)) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD);
        }

        return LabelledFoodResponse.from(food);
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
    public QueriedFoodsResponse findFoods(FoodsCondition condition) {
        Long totalCount = foodRepository.getTotalCountOfFoodEntitiesByQuery(condition);
        Long totalPage = condition.calculateTotalPage(totalCount);
        Long currentPage = condition.getPage();

        if (currentPage > totalPage) {
            throw new NonCriticalException(ExceptionInformation.NO_PAGE);
        }

        List<Food> foods = foodRepository.findFoodEntitiesByCondition(condition.calculateCursor(), condition);
        return QueriedFoodsResponse.from(totalCount, totalPage, currentPage, foods);
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
