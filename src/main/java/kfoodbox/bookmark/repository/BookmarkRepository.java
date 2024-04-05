package kfoodbox.bookmark.repository;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookmarkRepository {
    List<MyCommunityArticleBookmarksResponse.Bookmark> findBookmarkCommunityArticlesByUserId(@Param("id") Long id);
    List<MyCustomRecipeArticleBookmarksResponse.Bookmark> findBookmarkCustomRecipeArticlesByUserId(@Param("id") Long id);
    List<MyFoodBookmarksResponse.Bookmark> findBookmarkFoodsByUserId(@Param("id") Long id);
}
