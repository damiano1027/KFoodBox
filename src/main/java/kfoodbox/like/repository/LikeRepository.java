package kfoodbox.like.repository;

import kfoodbox.like.entity.CommunityArticleLike;
import org.apache.ibatis.annotations.Param;

public interface LikeRepository {
    void saveCommunityArticleLike(@Param("communityArticleLike") CommunityArticleLike communityArticleLike);
    CommunityArticleLike findCommunityArticleLikeByCommunityArticleIdAndUserId(@Param("communityArticleId") Long communityArticleId, @Param("userId") Long userId);
}
