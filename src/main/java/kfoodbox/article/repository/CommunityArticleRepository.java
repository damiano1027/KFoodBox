package kfoodbox.article.repository;

import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityArticleRepository {
    CommunityArticle findCommunityArticleById(@Param("id") Long id);
    void saveCommunityArticle(@Param("communityArticle") CommunityArticle communityArticle);
    void saveCommunityArticleImages(@Param("communityArticleImages") List<CommunityArticleImage> communityArticleImages);
}
