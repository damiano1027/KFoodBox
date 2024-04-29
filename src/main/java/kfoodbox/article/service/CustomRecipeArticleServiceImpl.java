package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.entity.CustomRecipeArticleImage;
import kfoodbox.article.entity.CustomRecipeIngredient;
import kfoodbox.article.entity.CustomRecipeSequence;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.food.entity.Food;
import kfoodbox.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomRecipeArticleServiceImpl implements CustomRecipeArticleService {
    private final CustomRecipeArticleRepository customRecipeArticleRepository;
    private final FoodRepository foodRepository;

    @Override
    @Transactional
    public void createCustomRecipeArticle(CustomRecipeArticleCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        if (!Objects.isNull(request.getFoodId())) {
            Food food = foodRepository.findFoodEntityById(request.getFoodId());
            if (food == null) {
                throw new NonCriticalException(ExceptionInformation.NO_FOOD);
            }
        }

        CustomRecipeArticle article = CustomRecipeArticle.from(request);
        article.changeUserId(userId);
        customRecipeArticleRepository.saveCustomRecipeArticle(article);

        List<CustomRecipeSequence> sequences = CustomRecipeSequence.from(article.getId(), request);
        customRecipeArticleRepository.saveCustomRecipeSequences(sequences);

        List<CustomRecipeIngredient> ingredients = CustomRecipeIngredient.from(article.getId(), request);
        customRecipeArticleRepository.saveCustomRecipeIngredients(ingredients);

        List<CustomRecipeArticleImage> images = CustomRecipeArticleImage.from(article.getId(), request);
        customRecipeArticleRepository.saveCustomRecipeArticleImages(images);
    }
}
