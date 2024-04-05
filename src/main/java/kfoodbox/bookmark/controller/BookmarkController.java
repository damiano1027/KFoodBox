package kfoodbox.bookmark.controller;

import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.service.BookmarkService;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Login(Authority.NORMAL)
    @GetMapping("/my-community-article-bookmarks")
    public ResponseEntity<MyCommunityArticleBookmarksResponse> getMyCommunityArticleBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyCommunityArticleBookmarks());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-custom-recipe-article-bookmarks")
    public ResponseEntity<MyCustomRecipeArticleBookmarksResponse> getMyCustomRecipeArticleBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyCustomRecipeArticleBookmarks());
    }
}
