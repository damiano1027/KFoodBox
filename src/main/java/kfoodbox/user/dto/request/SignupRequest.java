package kfoodbox.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank @Size(min = 1, max = 8)
    private String nickname;
    @NotNull
    private Long languageId;
}
