package kfoodbox.like.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.like.entity.CommunityArticleLike;
import kfoodbox.like.entity.CustomRecipeArticleLike;
import kfoodbox.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final CommunityArticleRepository communityArticleRepository;
    private final CustomRecipeArticleRepository customRecipeArticleRepository;

    @Override
    @Transactional
    public void createCommunityArticleLike(Long communityArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle article = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (article == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CommunityArticleLike existingLike = likeRepository.findCommunityArticleLikeByCommunityArticleIdAndUserId(communityArticleId, userId);
        if (existingLike != null) {
            throw new NonCriticalException(ExceptionInformation.LIKE_DUPLICATES);
        }

        CommunityArticleLike newLike = CommunityArticleLike.builder()
                .communityArticleId(communityArticleId)
                .userId(userId)
                .build();

        likeRepository.saveCommunityArticleLike(newLike);
    }

    @Override
    @Transactional
    public void deleteCommunityArticleLike(Long communityArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle article = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (article == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CommunityArticleLike existingLike = likeRepository.findCommunityArticleLikeByCommunityArticleIdAndUserId(communityArticleId, userId);
        if (existingLike == null) {
            throw new NonCriticalException(ExceptionInformation.NO_LIKE);
        }

        likeRepository.deleteCommunityArticleLike(existingLike);
    }

    @Override
    @Transactional
    public void createCustomRecipeArticleLike(Long customRecipeArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle article = customRecipeArticleRepository.findCustomRecipeArticleEntityById(customRecipeArticleId);
        if (Objects.isNull(article)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CustomRecipeArticleLike existingLike = likeRepository.findCustomRecipeArticleLikeByCustomRecipeArticleIdAndUserId(customRecipeArticleId, userId);
        if (!Objects.isNull(existingLike)) {
            throw new NonCriticalException(ExceptionInformation.LIKE_DUPLICATES);
        }

        CustomRecipeArticleLike newLike = CustomRecipeArticleLike.builder()
                .customRecipeArticleId(customRecipeArticleId)
                .userId(userId)
                .build();

        likeRepository.saveCustomRecipeArticleLike(newLike);
    }

    @Override
    @Transactional
    public void deleteCustomRecipeArticleLike(Long customRecipeArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle article = customRecipeArticleRepository.findCustomRecipeArticleEntityById(customRecipeArticleId);
        if (Objects.isNull(article)) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CustomRecipeArticleLike existingLike = likeRepository.findCustomRecipeArticleLikeByCustomRecipeArticleIdAndUserId(customRecipeArticleId, userId);
        if (Objects.isNull(existingLike)) {
            throw new NonCriticalException(ExceptionInformation.NO_LIKE);
        }

        likeRepository.deleteCustomRecipeArticleLike(existingLike);
    }
}
