package kfoodbox.article.repository;

import kfoodbox.article.entity.CommunityArticle;
import org.apache.ibatis.annotations.Param;

public interface CommunityArticleRepository {
    CommunityArticle findCommunityArticleById(@Param("id") Long id);
}
