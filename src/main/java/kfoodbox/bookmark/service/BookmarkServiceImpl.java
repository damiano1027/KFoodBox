package kfoodbox.bookmark.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import kfoodbox.bookmark.entity.CommunityArticleBookmark;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final CommunityArticleRepository communityArticleRepository;

    @Override
    @Transactional
    public void createCommunityArticleBookmark(CommunityArticleBookmarkCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleById(request.getCommunityArticleId());
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CommunityArticleBookmark existingBookmark = bookmarkRepository.findCommunityArticleBookmarkByUserIdAndCommunityArticleId(userId, request.getCommunityArticleId());
        if (existingBookmark != null) {
            throw new NonCriticalException(ExceptionInformation.BOOKMARK_DUPLICATES);
        }

        CommunityArticleBookmark newBookmark = CommunityArticleBookmark.from(request);
        newBookmark.changeUserId(userId);
        bookmarkRepository.saveCommunityArticleBookmark(newBookmark);
    }

    @Override
    @Transactional(readOnly = true)
    public MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        return new MyCommunityArticleBookmarksResponse(bookmarkRepository.findBookmarkCommunityArticlesByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        return new MyCustomRecipeArticleBookmarksResponse(bookmarkRepository.findBookmarkCustomRecipeArticlesByUserId(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public MyFoodBookmarksResponse getMyFoodBookmarks() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        return new MyFoodBookmarksResponse(bookmarkRepository.findBookmarkFoodsByUserId(userId));
    }
}
