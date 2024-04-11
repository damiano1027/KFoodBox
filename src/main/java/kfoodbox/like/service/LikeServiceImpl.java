package kfoodbox.like.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.like.entity.CommunityArticleLike;
import kfoodbox.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final CommunityArticleRepository communityArticleRepository;

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
}
