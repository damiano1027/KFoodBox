package kfoodbox.bookmark.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyCommunityArticleBookmarksResponse {
    @Schema(description = "북마크 리스트")
    private List<Bookmark> bookmarks;

    @Getter
    public static class Bookmark {
        @Schema(description = "게시물 id")
        private Long id;
        @Schema(description = "제목")
        private String title;
    }
}
