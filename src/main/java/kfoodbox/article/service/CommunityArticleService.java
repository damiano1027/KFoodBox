package kfoodbox.article.service;

import kfoodbox.article.dto.request.CommunityArticleCreateRequest;

public interface CommunityArticleService {
    void createCommunityArticle(CommunityArticleCreateRequest request);
}
