package kfoodbox.file.service;

import kfoodbox.file.domain.FileUploadType;
import kfoodbox.file.dto.ImageUrlsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ImageUrlsResponse uploadImages(List<MultipartFile> multipartFiles, FileUploadType fileUploadType) throws IOException;
}
