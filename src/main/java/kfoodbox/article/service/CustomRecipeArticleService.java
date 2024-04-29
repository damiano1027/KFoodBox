package kfoodbox.article.service;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;

public interface CustomRecipeArticleService {
    void createCustomRecipeArticle(CustomRecipeArticleCreateRequest request);
}
