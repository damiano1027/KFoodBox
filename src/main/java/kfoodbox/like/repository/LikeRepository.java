package kfoodbox.like.repository;

import kfoodbox.like.entity.CommunityArticleLike;
import org.apache.ibatis.annotations.Param;

public interface LikeRepository {
    CommunityArticleLike findCommunityArticleLikeByCommunityArticleIdAndUserId(@Param("communityArticleId") Long communityArticleId, @Param("userId") Long userId);
}
