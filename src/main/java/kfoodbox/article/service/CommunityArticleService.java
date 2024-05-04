package kfoodbox.article.service;

import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;
import kfoodbox.article.dto.request.CommunityArticlesCondition;
import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.request.CommunityCommentUpdateRequest;
import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.dto.response.CommunityArticlesResponse;
import kfoodbox.article.dto.response.CommunityCommentsResponse;

public interface CommunityArticleService {
    void createCommunityArticle(CommunityArticleCreateRequest request);
    CommunityArticleResponse getCommunityArticle(Long communityArticleId);
    void updateCommunityArticle(Long communityArticleId, CommunityArticleUpdateRequest request);
    void deleteCommunityArticle(Long communityArticleId);
    void createCommunityComment(Long communityArticleId, CommunityCommentCreateRequest request);
    CommunityCommentsResponse getAllCommentsOfCommunityArticle(Long communityArticleId);
    void updateCommunityComment(Long communityCommentId, CommunityCommentUpdateRequest request);
    void deleteCommunityComment(Long communityCommentId);
    CommunityArticlesResponse getCommunityArticles(CommunityArticlesCondition condition);
}
