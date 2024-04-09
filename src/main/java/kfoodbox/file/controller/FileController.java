package kfoodbox.file.controller;

import kfoodbox.file.domain.FileUploadType;
import kfoodbox.file.dto.ImageUrlsResponse;
import kfoodbox.file.service.FileService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/images")
    public ResponseEntity<ImageUrlsResponse> uploadImage(@RequestPart("images") List<MultipartFile> multipartFiles, @RequestPart("type") String type, BindingResult bindingResult) throws Exception {
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
