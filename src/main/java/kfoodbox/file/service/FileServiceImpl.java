package kfoodbox.file.service;

import kfoodbox.common.util.FileUploader;
import kfoodbox.file.domain.FileUploadType;
import kfoodbox.file.dto.ImageUrlsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileUploader fileUploader;

    @Override
    public ImageUrlsResponse uploadImages(List<MultipartFile> multipartFiles, FileUploadType fileUploadType) throws IOException {
        List<String> imageUrls = fileUploader.uploadFiles(multipartFiles, fileUploadType);
        return new ImageUrlsResponse(imageUrls);
    }
}
