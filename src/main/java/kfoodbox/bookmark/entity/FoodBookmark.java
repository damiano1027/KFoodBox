package kfoodbox.bookmark.entity;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class FoodBookmark {
    private Long id;
    private Long userId;
    private Long foodId;

    public static FoodBookmark from(Long foodId) {
        return FoodBookmark.builder()
                .foodId(foodId)
                .build();
    }

    public void changeUserId(Long userId) {
        this.userId = userId;
    }
}
