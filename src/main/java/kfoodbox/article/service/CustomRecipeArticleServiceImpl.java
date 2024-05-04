package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleUpdateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticlesCondition;
import kfoodbox.article.dto.request.CustomRecipeCommentCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeCommentUpdateRequest;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.dto.response.CustomRecipeArticlesResponse;
import kfoodbox.article.dto.response.CustomRecipeCommentsResponse;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.entity.CustomRecipeArticleImage;
import kfoodbox.article.entity.CustomRecipeComment;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (!images.isEmpty()) {
            customRecipeArticleRepository.saveCustomRecipeArticleImages(images);
        }
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
    public void updateCustomRecipeArticle(Long customRecipeArticleId, CustomRecipeArticleUpdateRequest request) {
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

        if (!Objects.isNull(request.getFoodId())) {
            Food food = foodRepository.findFoodEntityById(request.getFoodId());
            if (Objects.isNull(food)) {
                throw new NonCriticalException(ExceptionInformation.NO_FOOD);
            }
        }

        if (article.isUpdateRequired(request)) {
            article.update(request);
            customRecipeArticleRepository.updateCustomRecipeArticle(article);
        }

        List<CustomRecipeArticleImage> existingImages = customRecipeArticleRepository.findCustomRecipeArticleImagesByCustomRecipeArticleId(customRecipeArticleId);
        Set<String> existingImageUrlSet = CustomRecipeArticleImage.getUrlSet(existingImages);
        Set<String> requestedImageUrlSet = request.imageUrlSet();
        Set<String> copiedRequestedImageUrlSet = new HashSet<>(requestedImageUrlSet);

        requestedImageUrlSet.removeAll(existingImageUrlSet); // db에 삽입할 URL 집합을 추려냄
        existingImageUrlSet.removeAll(copiedRequestedImageUrlSet); // db에서 삭제할 URL 집합을 추려냄

        if (!requestedImageUrlSet.isEmpty()) {
            List<CustomRecipeArticleImage> additionalImages = CustomRecipeArticleImage.getList(customRecipeArticleId, requestedImageUrlSet);
            customRecipeArticleRepository.saveCustomRecipeArticleImages(additionalImages);
        }
        if (!existingImageUrlSet.isEmpty()) {
            customRecipeArticleRepository.deleteCustomRecipeArticleImages(customRecipeArticleId, new ArrayList<>(existingImageUrlSet));
        }

        // custom_recipe_sequence 테이블
        List<CustomRecipeSequence> existingSequences = customRecipeArticleRepository.findCustomRecipeSequencesByCustomRecipeArticleId(customRecipeArticleId);
        Set<Long> requestedSequencesNumberSet = request.getSequences().stream()
                .map(CustomRecipeArticleUpdateRequest.Sequence::getSequenceNumber)
                .collect(Collectors.toSet());

        List<CustomRecipeSequence> deletedSequences = new ArrayList<>();
        existingSequences.forEach(sequence -> {
            if (!requestedSequencesNumberSet.contains(sequence.getSequenceNumber())) {
                deletedSequences.add(sequence);
            }
        });
        if (!deletedSequences.isEmpty()) {
            customRecipeArticleRepository.deleteCustomRecipeSequences(deletedSequences);
        }

        List<CustomRecipeSequence> sequences = CustomRecipeSequence.from(customRecipeArticleId, request);
        customRecipeArticleRepository.saveCustomRecipeSequences(sequences);
        customRecipeArticleRepository.updateCustomRecipeSequences(sequences);

        // custom_recipe_ingredient 테이블
        List<CustomRecipeIngredient> existingIngredients = customRecipeArticleRepository.findCustomRecipeIngredientsByCustomRecipeArticleId(customRecipeArticleId);
        Set<String> requestedIngredientNameSet = request.getIngredients().stream()
                .map(CustomRecipeArticleUpdateRequest.Ingredient::getName)
                .collect(Collectors.toSet());

        List<CustomRecipeIngredient> deletedIngredients = new ArrayList<>();
        existingIngredients.forEach(ingredient -> {
            if (!requestedIngredientNameSet.contains(ingredient.getName())) {
                deletedIngredients.add(ingredient);
            }
        });
        if (!deletedIngredients.isEmpty()) {
            customRecipeArticleRepository.deleteCustomRecipeIngredients(deletedIngredients);
        }

        List<CustomRecipeIngredient> ingredients = CustomRecipeIngredient.from(customRecipeArticleId, request);
        customRecipeArticleRepository.saveCustomRecipeIngredients(ingredients);
        customRecipeArticleRepository.updateCustomRecipeIngredients(ingredients);
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

    @Override
    @Transactional
    public void createCustomRecipeComment(Long customRecipeArticleId, CustomRecipeCommentCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle article = customRecipeArticleRepository.findCustomRecipeArticleEntityById(customRecipeArticleId);
        if (Objects.isNull(article)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CustomRecipeComment comment = CustomRecipeComment.from(request);
        comment.changeCustomRecipeArticleId(customRecipeArticleId);
        comment.changeUserId(userId);

        customRecipeArticleRepository.saveCustomRecipeComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomRecipeCommentsResponse getAllCommentsOfCustomRecipeArticle(Long customRecipeArticleId) {
        CustomRecipeArticle article = customRecipeArticleRepository.findCustomRecipeArticleEntityById(customRecipeArticleId);
        if (Objects.isNull(article)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        List<CustomRecipeCommentsResponse.Comment> comments = customRecipeArticleRepository.findCustomRecipeCommentsByCustomRecipeArticleId(customRecipeArticleId);
        for (CustomRecipeCommentsResponse.Comment comment : comments) {
            comment.setIsMine(comment.getUserId() != null && comment.getUserId().equals(userId));
        }

        return new CustomRecipeCommentsResponse(comments);
    }

    @Override
    @Transactional
    public void updateCustomRecipeComment(Long customRecipeCommentId, CustomRecipeCommentUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeComment comment = customRecipeArticleRepository.findCustomRecipeCommentEntityById(customRecipeCommentId);
        if (Objects.isNull(comment)) {
            throw new NonCriticalException(ExceptionInformation.NO_COMMENT);
        }
        if (!comment.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        if (comment.isUpdateRequired(request)) {
            comment.update(request);
            customRecipeArticleRepository.updateCustomRecipeComment(comment);
        }
    }

    @Override
    @Transactional
    public void deleteCustomRecipeComment(Long customRecipeCommentId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeComment comment = customRecipeArticleRepository.findCustomRecipeCommentEntityById(customRecipeCommentId);
        if (Objects.isNull(comment)) {
            throw new NonCriticalException(ExceptionInformation.NO_COMMENT);
        }
        if (!comment.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        customRecipeArticleRepository.deleteCustomRecipeCommentById(customRecipeCommentId);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomRecipeArticlesResponse getCustomRecipeArticles(CustomRecipeArticlesCondition condition) {
        Long totalCount = customRecipeArticleRepository.getTotalCountOfCustomRecipeArticlesByCondition(condition);
        Long totalPage = condition.calculateTotalPage(totalCount);
        Long currentPage = condition.getPage();

        if (currentPage > totalPage) {
            throw new NonCriticalException(ExceptionInformation.NO_PAGE);
        }

        List<CustomRecipeArticlesResponse.Article> articles = customRecipeArticleRepository.findCustomRecipeArticlesByCondition(condition.calculateCursor(), condition);
        return CustomRecipeArticlesResponse.from(totalCount, totalPage, currentPage, articles);
    }
}
