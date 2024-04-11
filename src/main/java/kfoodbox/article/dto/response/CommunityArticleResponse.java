package kfoodbox.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CommunityArticleResponse {
    @Schema(description = "고유 id")
    private Long id;

    @Schema(description = "글쓴이 id")
    private Long userId;

    @Schema(description = "자신의 작성한 게시물인지 여부")
    private Boolean isMine;

    @Schema(description = "북마크 여부")
    private Boolean isBookmarked;

    @Schema(description = "좋아요 여부")
    private Boolean like;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성 날짜 및 시간 (UTC±00:00)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Schema(description = "수정 날짜 및 시간 (UTC±00:00)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @Schema(description = "작성자 닉네임")
    private String nickname;

    @Schema(description = "좋아요 수")
    private Long likeCount;

    @Schema(description = "댓글 수")
    private Long commentCount;

    public void resetCount() {
        if (likeCount == null) {
            likeCount = 0L;
        }
        if (commentCount == null) {
            commentCount = 0L;
        }
    }
}
