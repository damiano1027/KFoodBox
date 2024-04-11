package kfoodbox.bookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kfoodbox.bookmark.dto.response.MyCommunityArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyCustomRecipeArticleBookmarksResponse;
import kfoodbox.bookmark.dto.response.MyFoodBookmarksResponse;
import kfoodbox.bookmark.service.BookmarkService;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "북마크", description = "북마크 API")
@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Login(Authority.NORMAL)
    @PostMapping("/community-articles/{id}/bookmark")
    @Operation(summary = "자유게시판 게시물 북마크 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 없음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 북마크가 되어있음 (BOOKMARK_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCommunityArticleBookmark(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        bookmarkService.createCommunityArticleBookmark(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/custom-recipe-articles/{id}/bookmark")
    @Operation(summary = "나만의 레시피 게시판 게시물 북마크 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 없음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 북마크가 되어있음 (BOOKMARK_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCustomRecipeArticleBookmark(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        bookmarkService.createCustomRecipeArticleBookmark(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/foods/{id}/bookmark")
    @Operation(summary = "음식 북마크 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "음식 정보가 없음 (NO_FOOD)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 북마크가 되어있음 (BOOKMARK_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createFoodBookmark(@PathVariable("id") @Schema(description = "음식 id") Long id) {
        bookmarkService.createFoodBookmark(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-community-article-bookmarks")
    @Operation(summary = "나의 자유게시판 게시물 북마크 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyCommunityArticleBookmarksResponse> getMyCommunityArticleBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyCommunityArticleBookmarks());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-custom-recipe-article-bookmarks")
    @Operation(summary = "나의 나만의 레시피 게시판 게시물 북마크 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyCustomRecipeArticleBookmarksResponse> getMyCustomRecipeArticleBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyCustomRecipeArticleBookmarks());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-food-bookmarks")
    @Operation(summary = "나의 음식 북마크 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyFoodBookmarksResponse> getMyFoodBookmarks() {
        return ResponseEntity.ok(bookmarkService.getMyFoodBookmarks());
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/community-articles/{id}/bookmark")
    @Operation(summary = "자유게시판 게시물 북마크 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 없음 (NO_ARTICLE) | 북마크가 없음 (NO_BOOKMARK)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCommunityArticleBookmark(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        bookmarkService.deleteCommunityArticleBookmark(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/custom-recipe-articles/{id}/bookmark")
    @Operation(summary = "나만의 레시피 게시판 게시물 북마크 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 없음 (NO_ARTICLE) | 북마크가 없음 (NO_BOOKMARK)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCustomRecipeArticleBookmark(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        bookmarkService.deleteCustomRecipeArticleBookmark(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/foods/{id}/bookmark")
    @Operation(summary = "음식 북마크 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "음식 정보가 없음 (NO_FOOD) | 북마크가 없음 (NO_BOOKMARK)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteFoodBookmark(@PathVariable("id") @Schema(description = "음식 id") Long id) {
        bookmarkService.deleteFoodBookmark(id);
        return ResponseEntity.ok(null);
    }
}
