package kfoodbox.food.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.bookmark.entity.FoodBookmark;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.food.dto.request.FoodsCondition;
import kfoodbox.food.dto.request.KoreaRestaurantsCondition;
import kfoodbox.food.dto.response.AllFoodCategoriesResponse;
import kfoodbox.food.dto.response.FoodCategoryResponse;
import kfoodbox.food.dto.response.FoodResponse;
import kfoodbox.food.dto.response.FoodsResponse;
import kfoodbox.food.dto.response.KoreaRestaurantsResponse;
import kfoodbox.food.dto.response.LabelledFoodResponse;
import kfoodbox.food.dto.response.QueriedFoodsResponse;
import kfoodbox.food.dto.response.RecommendedFoodsResponse;
import kfoodbox.food.entity.Food;
import kfoodbox.food.entity.FoodImage;
import kfoodbox.food.entity.KoreaRegion;
import kfoodbox.food.entity.KoreaRestaurant;
import kfoodbox.food.entity.KoreaRestaurantTag;
import kfoodbox.food.entity.RestaurantCategory;
import kfoodbox.food.repository.FoodRepository;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

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

    @Override
    @Transactional(readOnly = true)
    public RecommendedFoodsResponse getRecommendedFoods() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        List<Food> allFoods = foodRepository.findAllFoodEntities();

        // 게스트 모드라면 랜덤으로 최대 3개의 음식을 골라 응답한다.
        if (Objects.isNull(userId)) {
            Random random = new Random();
            Set<Integer> foodIndexSet = new HashSet<>();
            int maxSize;

            if (allFoods.isEmpty()) {
                return RecommendedFoodsResponse.empty();
            } else {
                maxSize = Math.min(allFoods.size(), 3);
            }

            while (foodIndexSet.size() < maxSize) {
                foodIndexSet.add(random.nextInt(allFoods.size()));
            }

            List<Food> foods = new ArrayList<>();
            for (Integer index : foodIndexSet) {
                foods.add(allFoods.get(index));
            }

            List<FoodImage> foodImages = new ArrayList<>();
            for (Food food : foods) {
                foodImages.add(foodRepository.findTopFoodImageByFoodId(food.getId()));
            }

            return RecommendedFoodsResponse.from(foods, foodImages);
        }

        User me = userRepository.findUserById(userId);
        List<User> allUsers = userRepository.findAllUsers();
        List<FoodBookmark> allFoodBookmarks = bookmarkRepository.findAllFoodBookmarks();

        // 음식 id에 대한 list index를 매핑하는 map
        Map<Long, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < allFoods.size(); i++) {
            Food food = allFoods.get(i);
            indexMap.put(food.getId(), i);
        }

        // 벡터 map
        Map<Long, double[]> vectorMap = new HashMap<>();
        for (User user : allUsers) {
            vectorMap.put(user.getId(), new double[allFoods.size()]);
        }

        // 북마크 정보에 따라 벡터 최신화
        for (FoodBookmark bookmark : allFoodBookmarks) {
            int index = indexMap.get(bookmark.getFoodId());
            vectorMap.get(bookmark.getUserId())[index] = 1.0;
        }

        // 자신의 벡터와 다른 회원들과의 벡터에 대한 코사인 유사도 계산
        Queue<SimilarityInformation> similarityInformationQueue = new PriorityQueue<>();
        double[] myVector = vectorMap.get(userId);
        for (Long otherUserId : vectorMap.keySet()) {
            if (userId.equals(otherUserId)) {
                continue;
            }

            double[] otherUserVector = vectorMap.get(otherUserId);
            double cosineSimilarity = calculateCosineSimilarity(myVector, otherUserVector);
            similarityInformationQueue.add(new SimilarityInformation(cosineSimilarity, otherUserVector));
        }

        List<Food> foods = new ArrayList<>();
        while (!similarityInformationQueue.isEmpty()) {
            SimilarityInformation information = similarityInformationQueue.poll();
            double[] otherVector = information.vector;

            List<Food> tempFoods = new ArrayList<>();
            for (int i = 0; i < otherVector.length; i++) {
                if (otherVector[i] == 1.0 && myVector[i] == 0.0) {
                    tempFoods.add(allFoods.get(i));
                }
            }

            List<Food> randomFoods = getRandomFoods(tempFoods, 3 - foods.size());
            foods.addAll(randomFoods);

            if (foods.size() >= 3) {
                break;
            }
        }

        List<FoodImage> foodImages = new ArrayList<>();
        for (Food food : foods) {
            foodImages.add(foodRepository.findTopFoodImageByFoodId(food.getId()));
        }
        return RecommendedFoodsResponse.from(foods, foodImages);
    }

    @Override
    @Transactional(readOnly = true)
    public KoreaRestaurantsResponse findKoreaRestaurants(KoreaRestaurantsCondition condition) {
        KoreaRegion koreaRegion = foodRepository.findKoreaRegionById(condition.getKoreaRegionId());
        if (Objects.isNull(koreaRegion)) {
            throw new NonCriticalException(ExceptionInformation.NO_REGION);
        }

        RestaurantCategory restaurantCategory = foodRepository.findRestaurantCategoryById(condition.getRestaurantCategoryId());
        if (Objects.isNull(restaurantCategory)) {
            throw new NonCriticalException(ExceptionInformation.NO_RESTAURANT_CATEGORY);
        }

        KoreaRestaurantTag tag = foodRepository.findKoreaRestaurantTagByKoreaRegionIdAndRestaurantCategoryId(condition.getKoreaRegionId(), condition.getRestaurantCategoryId());
        List<KoreaRestaurant> restaurants = foodRepository.findKoreaRestaurantsByKoreaRestaurantTagId(tag.getId());

        return KoreaRestaurantsResponse.from(restaurants);
    }

    private static double calculateCosineSimilarity(double[] vector1, double[] vector2) {
        RealVector realVector1 = new ArrayRealVector(vector1);
        RealVector realVector2 = new ArrayRealVector(vector2);
        double dotProduct = realVector1.dotProduct(realVector2);

        return dotProduct == 0.0 ? 0.0 : dotProduct / (realVector1.getNorm() * realVector2.getNorm());
    }

    private static List<Food> getRandomFoods(List<Food> foods, int maxSize) {
        if (foods.size() <= maxSize) {
            return foods;
        }

        Set<Integer> indexSet = new HashSet<>();
        List<Food> randomFoods = new ArrayList<>();
        Random random = new Random();

        while (indexSet.size() < maxSize) {
            indexSet.add(random.nextInt(foods.size()));
        }
        for (int index : indexSet) {
            randomFoods.add(foods.get(index));
        }

        return randomFoods;
    }

    private static class SimilarityInformation implements Comparable<SimilarityInformation> {
        double similarity;
        double[] vector;

        SimilarityInformation(double similarity, double[] vector) {
            this.similarity = similarity;
            this.vector = vector;
        }

        @Override
        public int compareTo(SimilarityInformation o) {
            return Double.compare(o.similarity, similarity);
        }
    }
}
