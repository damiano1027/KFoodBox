package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;
import kfoodbox.article.dto.request.CommunityArticlesCondition;
import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.request.CommunityCommentUpdateRequest;
import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.dto.response.CommunityArticlesResponse;
import kfoodbox.article.dto.response.CommunityCommentsResponse;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import kfoodbox.article.entity.CommunityComment;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.bookmark.entity.CommunityArticleBookmark;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.like.entity.CommunityArticleLike;
import kfoodbox.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityArticleServiceImpl implements CommunityArticleService {
    private final CommunityArticleRepository communityArticleRepository;
    private final BookmarkRepository bookmarkRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void createCommunityArticle(CommunityArticleCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = CommunityArticle.from(request);
        communityArticle.changeUserId(userId);
        communityArticle.changeToNonNotice();
        communityArticleRepository.saveCommunityArticle(communityArticle);

        List<CommunityArticleImage> communityArticleImages = request.getImageUrls()
                .stream()
                .map(imageUrl -> CommunityArticleImage.from(communityArticle.getId(), imageUrl))
                .collect(Collectors.toList());

        if (!communityArticleImages.isEmpty()) {
            communityArticleRepository.saveCommunityArticleImages(communityArticleImages);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommunityArticleResponse getCommunityArticle(Long communityArticleId) {
        CommunityArticleResponse response = communityArticleRepository.findCommunityArticleById(communityArticleId);
        if (response == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        response.resetCount();

        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");
        response.setIsMine(response.getUserId() != null && response.getUserId().equals(userId));

        if (userId == null) {
            response.setIsBookmarked(false);
            response.setLike(false);
        } else {
            CommunityArticleBookmark bookmark = bookmarkRepository.findCommunityArticleBookmarkByUserIdAndCommunityArticleId(userId, communityArticleId);
            response.setIsBookmarked(bookmark != null);
            CommunityArticleLike like = likeRepository.findCommunityArticleLikeByCommunityArticleIdAndUserId(communityArticleId, userId);
            response.setLike(like != null);
        }

        return response;
    }

    @Override
    @Transactional
    public void updateCommunityArticle(Long communityArticleId, CommunityArticleUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }
        if (!communityArticle.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        if (communityArticle.isUpdateRequired(request)) {
            communityArticle.update(request);
            communityArticleRepository.updateCommunityArticle(communityArticle);
        }

        List<CommunityArticleImage> existingImages = communityArticleRepository.findCommunityArticleImagesByCommunityArticleId(communityArticleId);
        Set<String> existingImageUrlSet = CommunityArticleImage.getUrlSet(existingImages);
        Set<String> requestedImageUrlSet = request.imageUrlSet();
        Set<String> copiedRequestedImageUrlSet = new HashSet<>(requestedImageUrlSet);

        requestedImageUrlSet.removeAll(existingImageUrlSet); // db에 삽입할 URL 집합을 추려냄
        existingImageUrlSet.removeAll(copiedRequestedImageUrlSet); // db에서 삭제할 URL 집합을 추려냄

        if (!requestedImageUrlSet.isEmpty()) {
            List<CommunityArticleImage> additionalCommunityArticleImages = CommunityArticleImage.getList(communityArticleId, requestedImageUrlSet);
            communityArticleRepository.saveCommunityArticleImages(additionalCommunityArticleImages);
        }
        if (!existingImageUrlSet.isEmpty()) {
            communityArticleRepository.deleteCommunityArticleImages(communityArticleId, new ArrayList<>(existingImageUrlSet));
        }
    }

    @Override
    @Transactional
    public void deleteCommunityArticle(Long communityArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }
        if (!communityArticle.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        // community_article 테이블 레코드 삭제시 community_article_image 테이블에 있는 연관 레코드도 삭제됨
        communityArticleRepository.deleteCommunityArticleById(communityArticleId);
    }

    @Override
    @Transactional
    public void createCommunityComment(Long communityArticleId, CommunityCommentCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CommunityComment communityComment = CommunityComment.from(request);
        communityComment.changeCommunityArticleId(communityArticleId);
        communityComment.changeUserId(userId);

        communityArticleRepository.saveCommunityComment(communityComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommunityCommentsResponse getAllCommentsOfCommunityArticle(Long communityArticleId) {
        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleEntityById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        List<CommunityCommentsResponse.Comment> comments = communityArticleRepository.findCommunityCommentsByCommunityArticleId(communityArticleId);
        for (CommunityCommentsResponse.Comment comment : comments) {
            comment.setIsMine(comment.getUserId() != null && comment.getUserId().equals(userId));
        }

        return new CommunityCommentsResponse(comments);
    }

    @Override
    @Transactional
    public void updateCommunityComment(Long communityCommentId, CommunityCommentUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityComment communityComment = communityArticleRepository.findCommunityCommentEntityById(communityCommentId);
        if (communityComment == null) {
            throw new NonCriticalException(ExceptionInformation.NO_COMMENT);
        }
        if (!communityComment.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        if (communityComment.isUpdateRequired(request)) {
            communityComment.update(request);
            communityArticleRepository.updateCommunityComment(communityComment);
        }
    }

    @Override
    @Transactional
    public void deleteCommunityComment(Long communityCommentId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityComment communityComment = communityArticleRepository.findCommunityCommentEntityById(communityCommentId);
        if (communityComment == null) {
            throw new NonCriticalException(ExceptionInformation.NO_COMMENT);
        }
        if (!communityComment.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        communityArticleRepository.deleteCommunityCommentById(communityCommentId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommunityArticlesResponse getCommunityArticles(CommunityArticlesCondition condition) {
        Long totalCount = communityArticleRepository.getTotalCountOfCommunityArticlesByCondition(condition);
        Long totalPage = condition.calculateTotalPage(totalCount);
        Long currentPage = condition.getPage();

        if (currentPage > totalPage) {
            throw new NonCriticalException(ExceptionInformation.NO_PAGE);
        }

        List<CommunityArticlesResponse.Article> articles = communityArticleRepository.findCommunityArticlesByCondition(condition.calculateCursor(), condition);
        return CommunityArticlesResponse.from(totalCount, totalPage, currentPage, articles);
    }
}
