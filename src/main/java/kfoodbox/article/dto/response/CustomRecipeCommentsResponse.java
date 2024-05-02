package kfoodbox.article.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomRecipeCommentsResponse {
    @Schema(description = "댓글 리스트")
    private List<Comment> comments;

    @Getter @Setter
    public static class Comment {
        @Schema(description = "댓글 id")
        private Long id;
        @Schema(description = "글쓴이 id")
        private Long userId;
        @Schema(description = "자신이 쓴 댓글인지 여부")
        private Boolean isMine;
        @Schema(description = "닉네임")
        private String nickname;
        @Schema(description = "작성 날짜 및 시간 (UTC±00:00)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdAt;
        @Schema(description = "수정 날짜 및 시간 (UTC±00:00)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date updatedAt;
        @Schema(description = "내용")
        private String content;
    }
}
