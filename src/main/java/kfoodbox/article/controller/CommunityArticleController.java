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
import kfoodbox.article.dto.request.CommunityArticlesCondition;
import kfoodbox.article.dto.request.CommunityCommentCreateRequest;
import kfoodbox.article.dto.request.CommunityCommentUpdateRequest;
import kfoodbox.article.dto.response.CommunityArticleResponse;
import kfoodbox.article.dto.response.CommunityArticlesResponse;
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
    @Operation(summary = "자유게시판 게시물 조회", description = "nickname이 null이라면 해당 회원이 탈퇴한 경우임")
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
    @Operation(summary = "특정 자유게시판 게시물의 모든 댓글 조회", description = "nickname이 null이라면 해당 회원이 탈퇴한 경우임")
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

    @Login(Authority.NORMAL)
    @DeleteMapping("/community-comments/{id}")
    @Operation(summary = "자유게시판 게시물 댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음 (NO_COMMENT)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCommunityComment(@PathVariable("id") @Schema(description = "댓글 id") Long id) {
        communityArticleService.deleteCommunityComment(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/community-articles")
    @Operation(summary = "자유게시판 게시물 리스트 조회", description = "- page (페이지 번호)\n" +
                                                                 "  - Not null\n "+
                                                                 "  - 양의 정수\n" +
                                                                 "- limit (한 페이지의 최대 게시물 수)\n" +
                                                                 "  - Not null\n" +
                                                                 "  - 최소: 1, 최대: 50\n" +
                                                                 "- type (전체 or 공지글 여부)\n" +
                                                                 "  - `ALL`: 전체\n" +
                                                                 "  - `NOTICE`: 공지\n" +
                                                                 "- sort (정렬 기준)\n" +
                                                                 "  - `LATEST`: 최신순\n" +
                                                                 "  - `OLDEST`: 오랜된순\n" +
                                                                 "  - `LIKES`: 좋아요순\n" +
                                                                 "  - `COMMENTS`: 댓글순\n" +
                                                                 "- query (검색어)\n" +
                                                                 "  - 제목, 내용에 대해 검색됨")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CommunityArticlesResponse> getCommunityArticles(@Valid CommunityArticlesCondition condition) {
        return ResponseEntity.ok(communityArticleService.getCommunityArticles(condition));
    }
}
