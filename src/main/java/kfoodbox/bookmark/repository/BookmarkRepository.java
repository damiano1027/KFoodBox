package kfoodbox.bookmark.repository;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookmarkRepository {
    List<MyCommunityArticleBookmarksResponse.Bookmark> findBookmarkCommunityArticlesByUserId(@Param("id") Long id);
}
