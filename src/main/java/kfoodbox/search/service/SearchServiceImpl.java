package kfoodbox.search.service;

import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.food.entity.Food;
import kfoodbox.food.repository.FoodRepository;
import kfoodbox.search.dto.response.IntegratedSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final FoodRepository foodRepository;
    private final CommunityArticleRepository communityArticleRepository;
    private final CustomRecipeArticleRepository customRecipeArticleRepository;

    @Override
    @Transactional(readOnly = true)
    public IntegratedSearchResponse searchIntegratedInformation(String query) {
        List<Food> foods = foodRepository.findFoodEntitiesByQuery(query);
        List<CommunityArticle> communityArticles = communityArticleRepository.findCommunityArticlesByQuery(query);
        List<CustomRecipeArticle> customRecipeArticles = customRecipeArticleRepository.findCustomRecipeArticlesByQuery(query);

        return IntegratedSearchResponse.of(foods, communityArticles, customRecipeArticles);
    }
}
