package kfoodbox.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kfoodbox.file.domain.FileUploadType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUploader {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.domain}")
    private String domain;

    public List<String> uploadFiles(List<MultipartFile> files, FileUploadType type) throws IOException {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String today = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
            String absoluteBucketPath = new StringBuilder()
                    .append(domain)
                    .append(type.getBucketPath())
                    .append(today)
                    .toString();

            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
            String fileName = new StringBuilder()
                    .append(System.currentTimeMillis())
                    .append("-")
                    .append(UUID.randomUUID())
                    .append(".")
                    .append(extension)
                    .toString();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(absoluteBucketPath, fileName, file.getInputStream(), metadata));

            String absoluteFilePath = new StringBuilder()
                    .append(absoluteBucketPath)
                    .append("/").append(fileName)
                    .toString();

            imageUrls.add(absoluteFilePath);
        }

        return imageUrls;
    }
}
