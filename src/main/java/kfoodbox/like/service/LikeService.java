package kfoodbox.like.service;

public interface LikeService {
    void createCommunityArticleLike(Long communityArticleId);
    void deleteCommunityArticleLike(Long communityArticleId);
    void createCustomRecipeArticleLike(Long customRecipeArticleId);
}
