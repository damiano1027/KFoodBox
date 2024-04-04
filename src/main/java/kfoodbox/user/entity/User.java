package kfoodbox.user.entity;

import kfoodbox.user.dto.request.SignupRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class User {
    private Long id;
    private Long languageId;
    private String email;
    private String password;
    private String nickname;
    private Boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;

    public static User from(SignupRequest request) {
        return User.builder()
                .languageId(request.getLanguageId())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        if (isAdmin == null) {
            return false;
        }

        return isAdmin;
    }
}
