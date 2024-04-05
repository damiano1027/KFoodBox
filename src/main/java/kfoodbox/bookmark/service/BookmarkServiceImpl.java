package kfoodbox.bookmark.service;

import jakarta.servlet.http.HttpServletRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.repository.BookmarkRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.request.RequestApproacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Override
    @Transactional(readOnly = true)
    public MyCommunityArticleBookmarksResponse getMyCommunityArticleBookmarks() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        return new MyCommunityArticleBookmarksResponse(bookmarkRepository.findBookmarkCommunityArticlesByUserId(userId));
    }
}
