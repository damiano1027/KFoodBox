package kfoodbox.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ImageUrlsResponse {
    private List<String> imageUrls;
}
