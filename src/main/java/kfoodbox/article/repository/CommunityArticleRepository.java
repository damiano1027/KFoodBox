package kfoodbox.article.repository;

import kfoodbox.article.dto.request.CommunityArticlesCondition;
import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.dto.response.CommunityArticlesResponse;
import kfoodbox.article.dto.response.CommunityCommentsResponse;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import kfoodbox.article.entity.CommunityComment;
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
    void saveCommunityComment(@Param("communityComment") CommunityComment communityComment);
    CommunityComment findCommunityCommentEntityById(@Param("id") Long id);
    void updateCommunityComment(@Param("communityComment") CommunityComment communityComment);
    List<CommunityCommentsResponse.Comment> findCommunityCommentsByCommunityArticleId(@Param("communityArticleId") Long communityArticleId);
    void deleteCommunityCommentById(@Param("id") Long id);
    Long getTotalCountOfCommunityArticlesByCondition(@Param("condition") CommunityArticlesCondition condition);
    List<CommunityArticlesResponse.Article> findCommunityArticlesByCondition(@Param("cursor") Long cursor, @Param("condition") CommunityArticlesCondition condition);
}
