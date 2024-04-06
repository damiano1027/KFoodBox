package kfoodbox.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameExistenceResponse {
    @Schema(description = "닉네임 존재 여부")
    private Boolean isExist;
}
