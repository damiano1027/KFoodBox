package kfoodbox.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @NotBlank @Size(min = 1, max = 8)
    private String nickname;
    @NotBlank
    private String password;
}
