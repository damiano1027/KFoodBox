package kfoodbox.article.service;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleUpdateRequest;
import kfoodbox.article.dto.request.CustomRecipeCommentCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeCommentUpdateRequest;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.dto.response.CustomRecipeCommentsResponse;

public interface CustomRecipeArticleService {
    void createCustomRecipeArticle(CustomRecipeArticleCreateRequest request);
    CustomRecipeArticleResponse getCustomRecipeArticle(Long customRecipeArticleId);
    void updateCustomRecipeArticle(Long customRecipeArticleId, CustomRecipeArticleUpdateRequest request);
    void deleteCustomRecipeArticle(Long customRecipeArticleId);
    void createCustomRecipeComment(Long customRecipeArticleId, CustomRecipeCommentCreateRequest request);
    CustomRecipeCommentsResponse getAllCommentsOfCustomRecipeArticle(Long customRecipeArticleId);
    void updateCustomRecipeComment(Long customRecipeCommentId, CustomRecipeCommentUpdateRequest request);
    void deleteCustomRecipeComment(Long customRecipeCommentId);
}
