package kfoodbox.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleUpdateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticlesCondition;
import kfoodbox.article.dto.request.CustomRecipeCommentCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeCommentUpdateRequest;
import kfoodbox.article.dto.response.CustomRecipeArticleResponse;
import kfoodbox.article.dto.response.CustomRecipeArticlesResponse;
import kfoodbox.article.dto.response.CustomRecipeCommentsResponse;
import kfoodbox.article.service.CustomRecipeArticleService;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.common.exception.UnprocessableEntityExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@Tag(name = "레시피 게시판", description = "레시피 게시판 API")
@RestController
@RequiredArgsConstructor
public class CustomRecipeArticleController {
    private final CustomRecipeArticleService customRecipeArticleService;

    @Login(Authority.NORMAL)
    @PostMapping("/custom-recipe-article")
    @Operation(summary = "레시피 게시판 게시물 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "food_id로 요청한 음식 정보가 존재하지 않음 (NO_FOOD)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCustomRecipeArticle(@RequestBody @Valid CustomRecipeArticleCreateRequest request, BindingResult bindingResult) throws Exception {
        // 레시피 순서 번호 중복 확인
        Set<Long> set = new HashSet<>();
        request.getSequences().forEach(sequence -> {
            set.add(sequence.getSequenceNumber());
        });
        if (set.size() != request.getSequences().size()) {
            bindingResult.addError(new FieldError("sequenceNumber", "sequenceNumber", "순서 번호가 중복되면 안됩니다."));
            throw new BindException(bindingResult);
        }

        customRecipeArticleService.createCustomRecipeArticle(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/custom-recipe-articles/{id}")
    @Operation(summary = "레시피 게시판 게시물 조회", description = "nickname이 null이라면 해당 회원이 탈퇴한 경우임")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CustomRecipeArticleResponse> getCustomRecipeArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        return ResponseEntity.ok(customRecipeArticleService.getCustomRecipeArticle(id));
    }

    @Login(Authority.NORMAL)
    @PutMapping("/custom-recipe-articles/{id}")
    @Operation(summary = "레시피 게시판 게시물 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE) | food_id로 요청한 음식 정보가 존재하지 않음 (NO_FOOD)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> updateCustomRecipeArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id, @RequestBody @Valid CustomRecipeArticleUpdateRequest request) {
        customRecipeArticleService.updateCustomRecipeArticle(id, request);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/custom-recipe-articles/{id}")
    @Operation(summary = "레시피 게시판 게시물 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCustomRecipeArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        customRecipeArticleService.deleteCustomRecipeArticle(id);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/custom-recipe-articles/{id}/comment")
    @Operation(summary = "레시피 게시판 특정 게시물에 댓글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> createCustomRecipeComment(@PathVariable("id") @Schema(description = "게시물 id") Long id, @RequestBody @Valid CustomRecipeCommentCreateRequest request) {
        customRecipeArticleService.createCustomRecipeComment(id, request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/custom-recipe-articles/{id}/comments")
    @Operation(summary = "특정 레시피 게시판 게시물의 모든 댓글 조회", description = "nickname이 null이라면 해당 회원이 탈퇴한 경우임")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물이 존재하지 않음 (NO_ARTICLE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CustomRecipeCommentsResponse> getAllCommentsOfCustomRecipeArticle(@PathVariable("id") @Schema(description = "게시물 id") Long id) {
        return ResponseEntity.ok(customRecipeArticleService.getAllCommentsOfCustomRecipeArticle(id));
    }

    @Login(Authority.NORMAL)
    @PutMapping("/custom-recipe-comments/{id}")
    @Operation(summary = "레시피 게시판 게시물 댓글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음 (NO_COMMENT)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> updateCustomRecipeComment(@PathVariable("id") @Schema(description = "댓글 id") Long id, @RequestBody @Valid CustomRecipeCommentUpdateRequest request) {
        customRecipeArticleService.updateCustomRecipeComment(id, request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @DeleteMapping("/custom-recipe-comments/{id}")
    @Operation(summary = "레시피 게시판 게시물 댓글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "댓글이 존재하지 않음 (NO_COMMENT)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> deleteCustomRecipeComment(@PathVariable("id") @Schema(description = "댓글 id") Long id) {
        customRecipeArticleService.deleteCustomRecipeComment(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/custom-recipe-articles")
    @Operation(summary = "레시피 게시판 게시물 리스트 조회", description = "- page (페이지 번호)\n" +
                                                                 "  - Not null\n "+
                                                                 "  - 양의 정수\n" +
                                                                 "- limit (한 페이지의 최대 게시물 수)\n" +
                                                                 "  - Not null\n" +
                                                                 "  - 최소: 1, 최대: 50\n" +
                                                                 "- type\n" +
                                                                 "  - Not null\n" +
                                                                 "  - `ALL`: 전체\n" +
                                                                 "- sort (정렬 기준)\n" +
                                                                 "  - Not null" +
                                                                 "  - `LATEST`: 최신순\n" +
                                                                 "  - `OLDEST`: 오랜된순\n" +
                                                                 "  - `LIKES`: 좋아요순\n" +
                                                                 "  - `COMMENTS`: 댓글순\n" +
                                                                 "- query (검색어)\n" +
                                                                 "  - 제목, 내용에 대해 검색됨\n" +
                                                                 "- foodId (음식 id)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<CustomRecipeArticlesResponse> getCustomRecipeArticles(@Valid CustomRecipeArticlesCondition condition) {
        return ResponseEntity.ok(customRecipeArticleService.getCustomRecipeArticles(condition));
    }
}
