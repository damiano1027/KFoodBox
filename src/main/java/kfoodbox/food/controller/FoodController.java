package kfoodbox.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "음식", description = "음식 API")
@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/foods/{id}")
    @Operation(summary = "음식 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "음식 정보 없음 (NO_FOOD)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<FoodResponse> findFood(@PathVariable("id") @Schema(description = "음식 id") Long id) {
        return ResponseEntity.ok(foodService.findFood(id));
    }

    @GetMapping("/food-categories/{id}/foods")
    @Operation(summary = "특정 음식 카테고리의 모든 음식 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "음식 카테고리 없음 (NO_FOOD_CATEGORY)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<FoodsResponse> findFoodsInCategory(@PathVariable("id") @Schema(description = "음식 카테고리 id") Long id) {
        return ResponseEntity.ok(foodService.findFoodsInCategory(id));
    }

    @GetMapping("/food-categories/{id}")
    @Operation(summary = "음식 카테고리 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "음식 카테고리 없음 (NO_FOOD_CATEGORY)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<FoodCategoryResponse> findFoodCategory(@PathVariable("id") @Schema(description = "음식 카테고리 id") Long id) {
        return ResponseEntity.ok(foodService.findFoodCategory(id));
    }

    @GetMapping("/food-categories")
    @Operation(summary = "모든 음식 카테고리 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<AllFoodCategoriesResponse> findAllFoodCategories() {
        return ResponseEntity.ok(foodService.findAllFoodCategories());
    }
}
