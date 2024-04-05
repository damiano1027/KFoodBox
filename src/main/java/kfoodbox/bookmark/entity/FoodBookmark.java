package kfoodbox.bookmark.entity;

import kfoodbox.bookmark.dto.request.FoodBookmarkCreateRequest;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class FoodBookmark {
    private Long id;
    private Long userId;
    private Long foodId;

    public static FoodBookmark from(FoodBookmarkCreateRequest request) {
        return FoodBookmark.builder()
                .foodId(request.getFoodId())
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
