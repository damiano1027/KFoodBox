package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.entity.CustomRecipeArticleImage;
import kfoodbox.article.entity.CustomRecipeIngredient;
import kfoodbox.article.entity.CustomRecipeSequence;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.bookmark.entity.CustomRecipeArticleBookmark;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.food.entity.Food;
import kfoodbox.food.repository.FoodRepository;
import kfoodbox.like.entity.CustomRecipeArticleLike;
import kfoodbox.like.repository.LikeRepository;
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
    private final BookmarkRepository bookmarkRepository;
    private final LikeRepository likeRepository;

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

    @Override
    @Transactional(readOnly = true)
    public CustomRecipeArticleResponse getCustomRecipeArticle(Long customRecipeArticleId) {
        CustomRecipeArticleResponse response = customRecipeArticleRepository.findCustomRecipeArticleById(customRecipeArticleId);
        if (Objects.isNull(response)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");
        response.setIsMine(response.getUserId() != null && response.getUserId().equals(userId));

        if (Objects.isNull(userId)) {
            response.setIsBookmarked(false);
            response.setLike(false);
        } else {
            CustomRecipeArticleBookmark bookmark = bookmarkRepository.findCustomRecipeArticleBookmarkByUserIdAndCustomRecipeArticleId(userId, customRecipeArticleId);
            response.setIsBookmarked(!Objects.isNull(bookmark));
            CustomRecipeArticleLike like = likeRepository.findCustomRecipeArticleLikeByCustomRecipeArticleIdAndUserId(customRecipeArticleId, userId);
            response.setLike(!Objects.isNull(like));
        }

        return response;
    }

    @Override
    @Transactional
    public void deleteCustomRecipeArticle(Long customRecipeArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle article = customRecipeArticleRepository.findCustomRecipeArticleEntityById(customRecipeArticleId);
        if (Objects.isNull(article)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }
        if (!article.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        // custom_recipe_article 테이블 레코드 삭제시 연관 테이블들도 삭제됨
        customRecipeArticleRepository.deleteCustomRecipeArticleById(customRecipeArticleId);
    }
}
