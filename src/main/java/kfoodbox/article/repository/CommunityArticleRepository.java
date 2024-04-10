package kfoodbox.article.repository;

import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityArticleRepository {
    CommunityArticle findCommunityArticleEntityById(@Param("id") Long id);
    CommunityArticleResponse findCommunityArticleById(@Param("id") Long id);
    void saveCommunityArticle(@Param("communityArticle") CommunityArticle communityArticle);
    void saveCommunityArticleImages(@Param("communityArticleImages") List<CommunityArticleImage> communityArticleImages);
    List<CommunityArticleImage> findCommunityArticleImagesByCommunityArticleId(@Param("communityArticleId") Long communityArticleId);
    void updateCommunityArticle(@Param("communityArticle") CommunityArticle communityArticle);
    void deleteCommunityArticleImages(@Param("communityArticleId") Long communityArticleId, @Param("urls") List<String> urls);
    void deleteCommunityArticleById(@Param("id") Long id);
}
