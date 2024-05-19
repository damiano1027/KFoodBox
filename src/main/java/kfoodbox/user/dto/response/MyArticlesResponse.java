package kfoodbox.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Builder
public class MyArticlesResponse {
    @Schema(description = "자유게시판 게시물 리스트")
    private List<CommunityArticle> communityArticles;
    @Schema(description = "레시피게시판 게시물 리스트")
    private List<CustomRecipeArticle> customRecipeArticles;

    @Getter @Builder
    public static class CommunityArticle {
        @Schema(description = "고유 id")
        private Long id;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "작성 날짜 및 시간 (UTC±00:00)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdAt;
    }

    @Getter @Builder
    public static class CustomRecipeArticle {
        @Schema(description = "고유 id")
        private Long id;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "작성 날짜 및 시간 (UTC±00:00)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdAt;
    }

    public static MyArticlesResponse of(List<kfoodbox.article.entity.CommunityArticle> communityArticleEntities, List<kfoodbox.article.entity.CustomRecipeArticle> customRecipeArticleEntities) {
        List<CommunityArticle> communityArticles = new ArrayList<>();
        List<CustomRecipeArticle> customRecipeArticles = new ArrayList<>();

        communityArticleEntities.forEach(entity -> {
            communityArticles.add(
                    CommunityArticle.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .createdAt(entity.getCreatedAt())
                            .build()
            );
        });
        customRecipeArticleEntities.forEach(entity -> {
            customRecipeArticles.add(
                    CustomRecipeArticle.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .createdAt(entity.getCreatedAt())
                            .build()
            );
        });

        return MyArticlesResponse.builder()
                .communityArticles(communityArticles)
                .customRecipeArticles(customRecipeArticles)
                .build();
    }
}
