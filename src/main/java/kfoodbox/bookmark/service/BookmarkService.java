package kfoodbox.bookmark.service;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;

public interface BookmarkService {
    MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks();
    MyCustomRecipeArticleBookmarksResponse getMyCustomRecipeArticleBookmarks();
}
