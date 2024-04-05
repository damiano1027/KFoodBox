package kfoodbox.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyFoodBookmarksResponse {
    private List<Bookmark> bookmarks;

    @Getter
    public static class Bookmark {
        private Long id;
        private String name;
    }
}
