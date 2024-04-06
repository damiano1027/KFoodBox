package kfoodbox.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupAuthenticationNumberVerityRequest {
    @NotBlank @Email
    @Schema(description = "이메일\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상\n" +
                          "- 공백문자만 있으면 안됨\n" +
                          "- 이메일 형식이어야 함")
    private String email;

    @NotBlank
    @Schema(description = "인증번호\n" +
                          "- Not null\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String authenticationNumber;
}
