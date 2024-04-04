package kfoodbox.user.entity;

import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.request.UserUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

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

    public void update(UserUpdateRequest request) {
        this.nickname = request.getNickname();
        this.password = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public boolean isIdSame(long id) {
        return this.id.equals(id);
    }

    public boolean isAdmin() {
        if (isAdmin == null) {
            return false;
        }

        return isAdmin;
    }
}
