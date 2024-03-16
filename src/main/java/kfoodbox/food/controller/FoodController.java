package kfoodbox.food.controller;

import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/food-categories/{id}/foods")
    public ResponseEntity<FoodsResponse> findFoodsInCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(foodService.findFoodsInCategory(id));
    }

    @GetMapping("/food-categories/{id}")
    public ResponseEntity<FoodCategoryResponse> findFoodCategory(@PathVariable("id") Long id) {
        return ResponseEntity.ok(foodService.findFoodCategory(id));
    }

    @GetMapping("/food-categories")
    public ResponseEntity<AllFoodCategoriesResponse> findAllFoodCategories() {
        return ResponseEntity.ok(foodService.findAllFoodCategories());
    }
}
