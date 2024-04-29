package kfoodbox.article.service;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;

public interface CustomRecipeArticleService {
    void createCustomRecipeArticle(CustomRecipeArticleCreateRequest request);
    CustomRecipeArticleResponse getCustomRecipeArticle(Long customRecipeArticleId);
    void deleteCustomRecipeArticle(Long customRecipeArticleId);
}
