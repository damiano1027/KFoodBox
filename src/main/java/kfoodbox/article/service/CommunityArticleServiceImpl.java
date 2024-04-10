package kfoodbox.article.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CommunityArticleImage;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.request.RequestApproacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
}
