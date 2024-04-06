package kfoodbox.bookmark.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FoodBookmarkDeleteRequest {
    @NotNull
    @Schema(description = "음식 id\n" +
                          "- Not null")
    private Long foodId;
}
