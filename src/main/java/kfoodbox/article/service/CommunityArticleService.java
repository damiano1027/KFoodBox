package kfoodbox.article.service;

import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;
import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.response.CommunityArticleResponse;

public interface CommunityArticleService {
    void createCommunityArticle(CommunityArticleCreateRequest request);
    CommunityArticleResponse getCommunityArticle(Long communityArticleId);
    void updateCommunityArticle(Long communityArticleId, CommunityArticleUpdateRequest request);
    void deleteCommunityArticle(Long communityArticleId);
    void createCommunityComment(Long communityArticleId, CommunityCommentCreateRequest request);
}
