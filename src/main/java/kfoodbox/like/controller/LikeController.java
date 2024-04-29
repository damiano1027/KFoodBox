package kfoodbox.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @Login(Authority.NORMAL)
    @PostMapping("/community-articles/{id}/like")
    @Operation(summary = "자유게시판 게시물 좋아요 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 좋아요가 되어있음 (LIKE_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCommunityArticleLike(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        likeService.createCommunityArticleLike(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/community-articles/{id}/like")
    @Operation(summary = "자유게시판 게시물 좋아요 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE) | 좋아요 정보가 없음 (NO_LIKE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCommunityArticleLike(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        likeService.deleteCommunityArticleLike(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/custom-recipe-articles/{id}/like")
    @Operation(summary = "레시피 게시판 게시물 좋아요 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 좋아요가 되어있음 (LIKE_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCustomRecipeArticleLike(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        likeService.createCustomRecipeArticleLike(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/custom-recipe-articles/{id}/like")
    @Operation(summary = "레시피 게시판 게시물 좋아요 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE) | 좋아요 정보가 없음 (NO_LIKE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCustomRecipeArticleLike(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        likeService.deleteCustomRecipeArticleLike(id);
        return ResponseEntity.ok(null);
    }
}
