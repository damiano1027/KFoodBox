package kfoodbox.bookmark.service;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;

public interface BookmarkService {
    void createCommunityArticleBookmark(Long communityArticleId);
    void createCustomRecipeArticleBookmark(Long customRecipeArticleId);
    void createFoodBookmark(Long foodId);
    MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks();
    MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks();
    MyFoodBookmarksResponse getMyFoodBookmarks();
    void deleteCommunityArticleBookmark(Long communityArticleId);
    void deleteCustomRecipeArticleBookmark(Long customRecipeArticleId);
    void deleteFoodBookmark(Long foodId);
}
