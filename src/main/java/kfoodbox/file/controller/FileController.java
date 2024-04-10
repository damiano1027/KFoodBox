package kfoodbox.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.common.exception.UnprocessableEntityExceptionResponse;
import kfoodbox.file.domain.FileUploadType;
import kfoodbox.file.dto.ImageUrlsResponse;
import kfoodbox.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Tag(name = "파일 업로드", description = "파일 업로드 API")
@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Login(Authority.NORMAL)
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 업로드", description = "- 각 파일의 최대 용량: 10MB\n- 모든 파일의 용량 합 최대: 10MB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "413", description = "파일 업로드 최대 용량 초과"),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<ImageUrlsResponse> uploadImage(
            @RequestPart("images") List<MultipartFile> multipartFiles,
            @RequestPart("type") @Schema(description = """
            - `FOOD`: 음식
            - `FOOD_CATEGORY`: 음식 카테고리
            - `COMMUNITY_ARTICLE`: 자유게시판 게시물
            - `CUSTOM_RECIPE_ARTICLE`: 나만의 레시피 게시판 게시물
            - `CUSTOM_RECIPE_SEQUENCE`: 나만의 레시피 순서""") String type,
            BindingResult bindingResult) throws Exception {
        if (multipartFiles.isEmpty()) {
            bindingResult.addError(new FieldError("images", "images", "파일을 1개 이상 업로드해야합니다."));
            throw new BindException(bindingResult);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.getContentType().startsWith("image")) {
                bindingResult.addError(new FieldError("images", "images", "이미지만 업로드 가능합니다."));
                throw new BindException(bindingResult);
            }
        }

        Optional<FileUploadType> fileUploadType = FileUploadType.from(type);
        if (fileUploadType.isEmpty()) {
            bindingResult.addError(new FieldError("type", "type", "존재하지 않는 타입입니다."));
            throw new BindException(bindingResult);
        }

        return ResponseEntity.ok(fileService.uploadImages(multipartFiles, fileUploadType.get()));
    }
}
