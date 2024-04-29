package kfoodbox.like.repository;

import kfoodbox.like.entity.CommunityArticleLike;
import kfoodbox.like.entity.CustomRecipeArticleLike;
import org.apache.ibatis.annotations.Param;

public interface LikeRepository {
    void saveCommunityArticleLike(@Param("communityArticleLike") CommunityArticleLike communityArticleLike);
    CommunityArticleLike findCommunityArticleLikeByCommunityArticleIdAndUserId(@Param("communityArticleId") Long communityArticleId, @Param("userId") Long userId);
    CustomRecipeArticleLike findCustomRecipeArticleLikeByCustomRecipeArticleIdAndUserId(@Param("customRecipeArticleId") Long customRecipeArticleId, @Param("userId") Long userId);
    void deleteCommunityArticleLike(@Param("communityArticleLike") CommunityArticleLike communityArticleLike);
}
