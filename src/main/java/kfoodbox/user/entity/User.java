package kfoodbox.user.entity;

import lombok.Getter;

import java.util.Date;

@Getter
public class User {
    private Long id;
    private Long languageId;
    private String email;
    private String password;
    private String nickname;
    private Boolean isAdmin;
    private Date createdAt;
    private Date updatedAt;
}
