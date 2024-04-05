package kfoodbox.bookmark.service;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;

public interface BookmarkService {
    MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks();
    MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks();
    MyFoodBookmarksResponse getMyFoodBookmarks();
}
