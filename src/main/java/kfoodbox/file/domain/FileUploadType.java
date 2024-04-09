package kfoodbox.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum FileUploadType {
    FOOD("/foods"),
    FOOD_CATEGORY("/food-categories"),
    COMMUNITY_ARTICLE("/community-article"),
    CUSTOM_RECIPE_ARTICLE("/custom-recipe-article"),
    CUSTOM_RECIPE_SEQUENCE("/custom-recipe-sequence");

    public static Optional<FileUploadType> from(String name) {
        for (FileUploadType fileUploadType : values()) {
            if (fileUploadType.name().equals(name)) {
                return Optional.of(fileUploadType);
            }
        }
        return Optional.empty();
    }

    private final String bucketPath;
}
