package kfoodbox.bookmark.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyFoodBookmarksResponse {
    @Schema(description = "북마크 리스트")
    private List<Bookmark> bookmarks;

    @Getter
    public static class Bookmark {
        @Schema(description = "음식 id")
        private Long id;
        @Schema(description = "음식 이름")
        private String name;
    }
}
