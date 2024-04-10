package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityArticleServiceImpl implements CommunityArticleService {
    private final CommunityArticleRepository communityArticleRepository;

    @Override
    @Transactional
    public void createCommunityArticle(CommunityArticleCreateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = CommunityArticle.from(request);
        communityArticle.changeUserId(userId);
        communityArticle.changeToNonNotice();
        communityArticleRepository.saveCommunityArticle(communityArticle);

        List<CommunityArticleImage> communityArticleImages = request.getImageUrls()
                .stream()
                .map(imageUrl -> CommunityArticleImage.from(communityArticle.getId(), imageUrl))
                .collect(Collectors.toList());
        communityArticleRepository.saveCommunityArticleImages(communityArticleImages);
    }

    @Override
    @Transactional
    public void updateCommunityArticle(Long communityArticleId, CommunityArticleUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }
        if (!communityArticle.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        if (communityArticle.isUpdateRequired(request)) {
            communityArticle.update(request);
            communityArticleRepository.updateCommunityArticle(communityArticle);
        }

        List<CommunityArticleImage> existingImages = communityArticleRepository.findCommunityArticleImagesByCommunityArticleId(communityArticleId);
        Set<String> existingImageUrlSet = CommunityArticleImage.getUrlSet(existingImages);
        Set<String> requestedImageUrlSet = request.imageUrlSet();
        Set<String> copiedRequestedImageUrlSet = new HashSet<>(requestedImageUrlSet);

        requestedImageUrlSet.removeAll(existingImageUrlSet); // db에 삽입할 URL 집합을 추려냄
        existingImageUrlSet.removeAll(copiedRequestedImageUrlSet); // db에서 삭제할 URL 집합을 추려냄

        if (!requestedImageUrlSet.isEmpty()) {
            List<CommunityArticleImage> additionalCommunityArticleImages = CommunityArticleImage.getList(communityArticleId, requestedImageUrlSet);
            communityArticleRepository.saveCommunityArticleImages(additionalCommunityArticleImages);
        }
        if (!existingImageUrlSet.isEmpty()) {
            communityArticleRepository.deleteCommunityArticleImages(communityArticleId, new ArrayList<>(existingImageUrlSet));
        }
    }

    @Override
    @Transactional
    public void deleteCommunityArticle(Long communityArticleId) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        CommunityArticle communityArticle = communityArticleRepository.findCommunityArticleById(communityArticleId);
        if (communityArticle == null) {
            throw new NonCriticalException(ExceptionInformation.NO_ARTICLE);
        }
        if (!communityArticle.hasSameUserId(userId)) {
            throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
        }

        // community_article 테이블 레코드 삭제시 community_article_image 테이블에 있는 연관 레코드도 삭제됨
        communityArticleRepository.deleteCommunityArticleById(communityArticleId);
    }
}
