package kfoodbox.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyEmailResponse {
    @Schema(description = "이메일")
    private String email;
}
