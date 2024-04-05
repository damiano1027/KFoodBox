package kfoodbox.bookmark.repository;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import kfoodbox.bookmark.entity.CommunityArticleBookmark;
import kfoodbox.bookmark.entity.CustomRecipeArticleBookmark;
import kfoodbox.bookmark.entity.FoodBookmark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookmarkRepository {
    List<MyCommunityArticleBookmarksResponse.Bookmark> findBookmarkCommunityArticlesByUserId(@Param("id") Long id);
    List<MyCustomRecipeArticleBookmarksResponse.Bookmark> findBookmarkCustomRecipeArticlesByUserId(@Param("id") Long id);
    List<MyFoodBookmarksResponse.Bookmark> findBookmarkFoodsByUserId(@Param("id") Long id);
    CommunityArticleBookmark findCommunityArticleBookmarkByUserIdAndCommunityArticleId(@Param("userId") Long userId, @Param("communityArticleId") Long communityArticleId);
    CustomRecipeArticleBookmark findCustomRecipeArticleBookmarkByUserIdAndCustomRecipeArticleId(@Param("userId") Long userId, @Param("customRecipeArticleId") Long customRecipeArticleId);
    FoodBookmark findFoodBookmarkByUserIdAndFoodId(@Param("userId") Long userId, @Param("foodId") Long foodId);
    void saveCommunityArticleBookmark(@Param("communityArticleBookmark") CommunityArticleBookmark communityArticleBookmark);
    void saveCustomRecipeArticleBookmark(@Param("customRecipeArticleBookmark") CustomRecipeArticleBookmark customRecipeArticleBookmark);
    void saveFoodBookmark(@Param("foodBookmark") FoodBookmark foodBookmark);
    void deleteCommunityArticleBookmark(@Param("communityArticleBookmark") CommunityArticleBookmark communityArticleBookmark);
    void deleteCustomRecipeArticleBookmark(@Param("customRecipeArticleBookmark") CustomRecipeArticleBookmark customRecipeArticleBookmark);
    void deleteFoodBookmark(@Param("foodBookmark") FoodBookmark foodBookmark);
}
