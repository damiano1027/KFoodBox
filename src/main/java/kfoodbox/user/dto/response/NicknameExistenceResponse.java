package kfoodbox.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameExistenceResponse {
    private boolean isExist;
}
