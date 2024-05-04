package kfoodbox.article.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommunityArticlesCondition {
    @NotNull @Positive
    private Long page;

    @NotNull @Min(1) @Max(50)
    private Long limit;

    @NotNull
    private Type type;

    @NotNull
    private Sort sort;

    private String query;

    private enum Type {
        ALL, NOTICE
    }

    private enum Sort {
        LATEST, OLDEST, LIKES, COMMENTS
    }

    public Long calculateTotalPage(Long totalCount) {
        return totalCount.equals(0L) ? 1L : (long) Math.ceil((double) totalCount / this.limit);
    }

    public Long calculateCursor() {
        return (page - 1) * limit;
    }
}
