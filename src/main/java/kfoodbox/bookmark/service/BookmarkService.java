package kfoodbox.bookmark.service;

import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkDeleteRequest;
import kfoodbox.bookmark.dto.request.CustomRecipeArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.FoodBookmarkCreateRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;

public interface BookmarkService {
    void createCommunityArticleBookmark(CommunityArticleBookmarkCreateRequest request);
    void createCustomRecipeArticleBookmark(CustomRecipeArticleBookmarkCreateRequest request);
    void createFoodBookmark(FoodBookmarkCreateRequest request);
    MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks();
    MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks();
    MyFoodBookmarksResponse getMyFoodBookmarks();
    void deleteCommunityArticleBookmark(CommunityArticleBookmarkDeleteRequest request);
}
