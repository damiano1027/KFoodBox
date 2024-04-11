package kfoodbox.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kfoodbox.article.dto.request.CommunityArticleCreateRequest;
import kfoodbox.article.dto.request.CommunityArticleUpdateRequest;
import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.request.CommunityCommentUpdateRequest;
import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.dto.response.CommunityCommentsResponse;
import kfoodbox.article.service.CommunityArticleService;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.common.exception.UnprocessableEntityExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "자유게시판", description = "자유게시판 API")
@RestController
@RequiredArgsConstructor
public class CommunityArticleController {
    private final CommunityArticleService communityArticleService;

    @Login(Authority.NORMAL)
    @PostMapping("/community-article")
    @Operation(summary = "자유게시판 게시물 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCommunityArticle(@RequestBody @Valid CommunityArticleCreateRequest request) {
        communityArticleService.createCommunityArticle(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/community-articles/{id}")
    @Operation(summary = "자유게시판 게시물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CommunityArticleResponse> getCommunityArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        return ResponseEntity.ok(communityArticleService.getCommunityArticle(id));
    }

    @Login(Authority.NORMAL)
    @PutMapping("/community-articles/{id}")
    @Operation(summary = "자유게시판 게시물 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> updateCommunityArticle(@PathVariable("id") @Schema(description = "게시물 id") Long communityArticleId, @RequestBody @Valid CommunityArticleUpdateRequest request) {
        communityArticleService.updateCommunityArticle(communityArticleId, request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/community-articles/{id}")
    @Operation(summary = "자유게시판 게시물 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCommunityArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        communityArticleService.deleteCommunityArticle(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/community-articles/{id}/comment")
    @Operation(summary = "자유게시판 특정 게시물에 댓글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCommunityComment(@PathVariable("id") @Schema(description = "게시물 id") Long id, @RequestBody @Valid CommunityCommentCreateRequest request) {
        communityArticleService.createCommunityComment(id, request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/community-articles/{id}/comments")
    @Operation(summary = "특정 자유게시판 게시물의 모든 댓글 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CommunityCommentsResponse> getAllCommentsOfCommunityArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        return ResponseEntity.ok(communityArticleService.getAllCommentsOfCommunityArticle(id));
    }

    @Login(Authority.NORMAL)
    @PutMapping("/community-comments/{id}")
    @Operation(summary = "자유게시판 게시물 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음 (NO_COMMENT)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> updateCommunityComment(@PathVariable("id") @Schema(description = "댓글 id") Long id, @RequestBody @Valid CommunityCommentUpdateRequest request) {
        communityArticleService.updateCommunityComment(id, request);
        return ResponseEntity.ok(null);
    }
}
