package kfoodbox.article.repository;

import kfoodbox.article.dto.request.CustomRecipeArticlesCondition;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.dto.response.CustomRecipeArticlesResponse;
import kfoodbox.article.dto.response.CustomRecipeCommentsResponse;
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
    void updateCustomRecipeSequences(@Param("customRecipeSequences") List<CustomRecipeSequence> customRecipeSequences);
    void deleteCustomRecipeSequences(@Param("customRecipeSequences") List<CustomRecipeSequence> customRecipeSequences);
    void saveCustomRecipeIngredients(@Param("customRecipeIngredients") List<CustomRecipeIngredient> customRecipeIngredients);
    void saveCustomRecipeArticleImages(@Param("customRecipeArticleImages") List<CustomRecipeArticleImage> customRecipeArticleImages);
    List<CustomRecipeArticleImage> findCustomRecipeArticleImagesByCustomRecipeArticleId(@Param("customRecipeArticleId") Long customRecipeArticleId);
    CustomRecipeArticle findCustomRecipeArticleEntityById(@Param("id") Long id);
    CustomRecipeArticleResponse findCustomRecipeArticleById(@Param("id") Long id);
    void updateCustomRecipeArticle(@Param("customRecipeArticle") CustomRecipeArticle customRecipeArticle);
    void deleteCustomRecipeArticleImages(@Param("customRecipeArticleId") Long customRecipeArticleId, List<String> urls);
    void deleteCustomRecipeArticleById(@Param("id") Long id);
    void saveCustomRecipeComment(@Param("customRecipeComment") CustomRecipeComment customRecipeComment);
    CustomRecipeComment findCustomRecipeCommentEntityById(@Param("id") Long id);
    List<CustomRecipeCommentsResponse.Comment> findCustomRecipeCommentsByCustomRecipeArticleId(@Param("customRecipeArticleId") Long customRecipeArticleId);
    void updateCustomRecipeComment(@Param("customRecipeComment") CustomRecipeComment customRecipeComment);
    void deleteCustomRecipeCommentById(@Param("id") Long id);
    List<CustomRecipeSequence> findCustomRecipeSequencesByCustomRecipeArticleId(@Param("customRecipeArticleId") Long customRecipeArticleId);
    List<CustomRecipeIngredient> findCustomRecipeIngredientsByCustomRecipeArticleId(@Param("customRecipeArticleId") Long customRecipeArticleId);
    void deleteCustomRecipeIngredients(@Param("customRecipeIngredients") List<CustomRecipeIngredient> customRecipeIngredients);
    void updateCustomRecipeIngredients(@Param("customRecipeIngredients") List<CustomRecipeIngredient> customRecipeIngredients);
    Long getTotalCountOfCustomRecipeArticlesByCondition(@Param("condition") CustomRecipeArticlesCondition condition);
    List<CustomRecipeArticlesResponse.Article> findCustomRecipeArticlesByCondition(@Param("cursor") Long cursor, @Param("condition") CustomRecipeArticlesCondition condition);
    List<CustomRecipeArticle> findCustomRecipeArticlesByQuery(@Param("query") String query);
    List<CustomRecipeArticle> findCustomRecipeArticlesByUserId(@Param("userId") Long userId);
}
