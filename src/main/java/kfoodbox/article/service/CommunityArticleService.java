package kfoodbox.article.service;

import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;

public interface CommunityArticleService {
    void createCommunityArticle(CommunityArticleCreateRequest request);
    void updateCommunityArticle(Long communityArticleId, CommunityArticleUpdateRequest request);
    void deleteCommunityArticle(Long communityArticleId);
}
