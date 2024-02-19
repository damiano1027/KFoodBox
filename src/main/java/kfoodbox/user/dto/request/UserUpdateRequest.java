package kfoodbox.user.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String nickname;
    private String password;
}
