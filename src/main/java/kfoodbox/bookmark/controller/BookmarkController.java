package kfoodbox.bookmark.controller;

import jakarta.validation.Valid;
import kfoodbox.bookmark.dto.request.CommunityArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.CustomRecipeArticleBookmarkCreateRequest;
import kfoodbox.bookmark.dto.request.FoodBookmarkCreateRequest;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import kfoodbox.bookmark.service.BookmarkService;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Login(Authority.NORMAL)
    @PostMapping("/community-article-bookmark")
    public ResponseEntity<Void> createCommunityArticleBookmark(@RequestBody @Valid CommunityArticleBookmarkCreateRequest request) {
        bookmarkService.createCommunityArticleBookmark(request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/custom-recipe-article-bookmark")
    public ResponseEntity<Void> createCustomRecipeArticleBookmark(@RequestBody @Valid CustomRecipeArticleBookmarkCreateRequest request) {
        bookmarkService.createCustomRecipeArticleBookmark(request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/food-bookmark")
    public ResponseEntity<Void> createFoodBookmark(@RequestBody @Valid FoodBookmarkCreateRequest request) {
        bookmarkService.createFoodBookmark(request);
        return ResponseEntity.ok(null);
    }

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

    @Login(Authority.NORMAL)
    @GetMapping("/my-food-bookmarks")
    public ResponseEntity<MyFoodBookmarksResponse> getMyFoodBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyFoodBookmarks());
    }
}
