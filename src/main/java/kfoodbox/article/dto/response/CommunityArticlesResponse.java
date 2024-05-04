package kfoodbox.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter @Builder
public class CommunityArticlesResponse {
    @Schema(description = "조건에 해당하는 총 게시물의 개수")
    private Long totalCount;

    @Schema(description = "조건에 해당하는 상점중에 현재 페이지에서 조회된 개수")
    private Integer currentCount;

    @Schema(description = "조건에 해당하는 상점들을 조회할 수 있는 최대 페이지")
    private Long totalPage;

    @Schema(description = "현재 페이지")
    private Long currentPage;

    @Schema(description = "게시물 리스트")
    private List<Article> articles;

    @Getter
    public static class Article {
        @Schema(description = "게시물 id")
        private Long id;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "내용")
        private String content;

        @Schema(description = "좋아요 수")
        private Long likeCount;

        @Schema(description = "댓글 수")
        private Long commentCount;

        @Schema(description = "작성 날짜 및 시간 (UTC±00:00)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdAt;

        @Schema(description = "작성자 닉네임")
        private String nickname;
    }

    public static CommunityArticlesResponse from(Long totalCount, Long totalPage, Long currentPage, List<Article> articles) {
        return CommunityArticlesResponse.builder()
                .totalCount(totalCount)
                .currentCount(articles.size())
                .totalPage(totalPage)
                .currentPage(currentPage)
                .articles(articles)
                .build();
    }
}
