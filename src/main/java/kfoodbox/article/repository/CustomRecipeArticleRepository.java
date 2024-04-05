package kfoodbox.article.repository;

import kfoodbox.article.entity.CustomRecipeArticle;
import org.apache.ibatis.annotations.Param;

public interface CustomRecipeArticleRepository {
    CustomRecipeArticle findCustomRecipeArticleById(@Param("id") Long id);
}
