package kfoodbox.article.repository;

import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.entity.CustomRecipeArticleImage;
import kfoodbox.article.entity.CustomRecipeComment;
import kfoodbox.article.entity.CustomRecipeIngredient;
import kfoodbox.article.entity.CustomRecipeSequence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomRecipeArticleRepository {
    void saveCustomRecipeArticle(@Param("customRecipeArticle") CustomRecipeArticle customRecipeArticle);
    void saveCustomRecipeSequences(@Param("customRecipeSequences") List<CustomRecipeSequence> customRecipeSequences);
    void saveCustomRecipeIngredients(@Param("customRecipeIngredients") List<CustomRecipeIngredient> customRecipeIngredients);
    void saveCustomRecipeArticleImages(@Param("customRecipeArticleImages") List<CustomRecipeArticleImage> customRecipeArticleImages);
    CustomRecipeArticle findCustomRecipeArticleEntityById(@Param("id") Long id);
    CustomRecipeArticleResponse findCustomRecipeArticleById(@Param("id") Long id);
    void deleteCustomRecipeArticleById(@Param("id") Long id);
    void saveCustomRecipeComment(@Param("customRecipeComment") CustomRecipeComment customRecipeComment);
    CustomRecipeComment findCustomRecipeCommentEntityById(@Param("id") Long id);
    void updateCustomRecipeComment(@Param("customRecipeComment") CustomRecipeComment customRecipeComment);
    void deleteCustomRecipeCommentById(@Param("id") Long id);
}
