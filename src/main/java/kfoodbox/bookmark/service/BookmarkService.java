package kfoodbox.bookmark.service;

import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;

public interface BookmarkService {
    void createCommunityArticleBookmark(CommunityArticleBookmarkCreateRequest request);
    MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks();
    MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks();
    MyFoodBookmarksResponse getMyFoodBookmarks();
}
