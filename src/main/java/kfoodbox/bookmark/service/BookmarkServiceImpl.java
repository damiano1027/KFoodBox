package kfoodbox.bookmark.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkDeleteRequest;
import kfoodbox.bookmark.dto.request.CustomRecipeArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.CustomRecipeArticleBookmarkDeleteRequest;
import kfoodbox.bookmark.dto.request.FoodBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.FoodBookmarkDeleteRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import kfoodbox.bookmark.entity.CommunityArticleBookmark;
import kfoodbox.bookmark.entity.CustomRecipeArticleBookmark;
import kfoodbox.bookmark.entity.FoodBookmark;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.food.entity.Food;
import kfoodbox.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final CommunityArticleRepository communityArticleRepository;
    private final CustomRecipeArticleRepository customRecipeArticleRepository;
    private final FoodRepository foodRepository;

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
    @Transactional
    public void createCustomRecipeArticleBookmark(CustomRecipeArticleBookmarkCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle customRecipeArticle = customRecipeArticleRepository.findCustomRecipeArticleById(request.getCustomRecipeArticleId());
        if (customRecipeArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CustomRecipeArticleBookmark existingBookmark = bookmarkRepository.findCustomRecipeArticleBookmarkByUserIdAndCustomRecipeArticleId(userId, request.getCustomRecipeArticleId());
        if (existingBookmark != null) {
            throw new NonCriticalException(ExceptionInformation.BOOKMARK_DUPLICATES);
        }

        CustomRecipeArticleBookmark newBookmark = CustomRecipeArticleBookmark.from(request);
        newBookmark.changeUserId(userId);
        bookmarkRepository.saveCustomRecipeArticleBookmark(newBookmark);
    }

    @Override
    @Transactional
    public void createFoodBookmark(FoodBookmarkCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        Food food = foodRepository.findFoodEntityById(request.getFoodId());
        if (food == null) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD);
        }

        FoodBookmark existingBookmark = bookmarkRepository.findFoodBookmarkByUserIdAndFoodId(userId, request.getFoodId());
        if (existingBookmark != null) {
            throw new NonCriticalException(ExceptionInformation.BOOKMARK_DUPLICATES);
        }

        FoodBookmark newBookmark = FoodBookmark.from(request);
        newBookmark.changeUserId(userId);
        bookmarkRepository.saveFoodBookmark(newBookmark);
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

    @Override
    @Transactional
    public void deleteCommunityArticleBookmark(CommunityArticleBookmarkDeleteRequest request) {
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
        if (existingBookmark == null) {
            throw new NonCriticalException(ExceptionInformation.NO_BOOKMARK);
        }

        bookmarkRepository.deleteCommunityArticleBookmark(existingBookmark);
    }

    @Override
    @Transactional
    public void deleteCustomRecipeArticleBookmark(CustomRecipeArticleBookmarkDeleteRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CustomRecipeArticle customRecipeArticle = customRecipeArticleRepository.findCustomRecipeArticleById(request.getCustomRecipeArticleId());
        if (customRecipeArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }

        CustomRecipeArticleBookmark existingBookmark = bookmarkRepository.findCustomRecipeArticleBookmarkByUserIdAndCustomRecipeArticleId(userId, request.getCustomRecipeArticleId());
        if (existingBookmark == null) {
            throw new NonCriticalException(ExceptionInformation.NO_BOOKMARK);
        }

        bookmarkRepository.deleteCustomRecipeArticleBookmark(existingBookmark);
    }

    @Override
    @Transactional
    public void deleteFoodBookmark(FoodBookmarkDeleteRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        Food food = foodRepository.findFoodEntityById(request.getFoodId());
        if (food == null) {
            throw new NonCriticalException(ExceptionInformation.NO_FOOD);
        }

        FoodBookmark existingBookmark = bookmarkRepository.findFoodBookmarkByUserIdAndFoodId(userId, request.getFoodId());
        if (existingBookmark == null) {
            throw new NonCriticalException(ExceptionInformation.NO_BOOKMARK);
        }

        bookmarkRepository.deleteFoodBookmark(existingBookmark);
    }
}
