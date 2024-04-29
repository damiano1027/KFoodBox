package kfoodbox.article.repository;

import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.entity.CustomRecipeArticleImage;
import kfoodbox.article.entity.CustomRecipeIngredient;
import kfoodbox.article.entity.CustomRecipeSequence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomRecipeArticleRepository {
    void saveCustomRecipeArticle(@Param("customRecipeArticle") CustomRecipeArticle customRecipeArticle);
    void saveCustomRecipeSequences(@Param("customRecipeSequences") List<CustomRecipeSequence> customRecipeSequences);
    void saveCustomRecipeIngredients(@Param("customRecipeIngredients") List<CustomRecipeIngredient> customRecipeIngredients);
    void saveCustomRecipeArticleImages(@Param("customRecipeArticleImages") List<CustomRecipeArticleImage> customRecipeArticleImages);
    CustomRecipeArticle findCustomRecipeArticleById(@Param("id") Long id);
}
