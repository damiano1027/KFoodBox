package kfoodbox.user.service;

import kfoodbox.user.dto.request.LoginRequest;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.dto.response.MyEmailResponse;
import kfoodbox.user.dto.response.MyNicknameResponse;
import kfoodbox.user.dto.response.NicknameExistenceResponse;

public interface UserService {
    EmailExistenceResponse getExistenceOfEmail(String email);
    NicknameExistenceResponse getExistenceOfNickname(String nickname);
    void signUp(SignupRequest request);
    LanguagesResponse getAllLanguages();
    void login(LoginRequest request);
    void logout();
    MyEmailResponse getMyEmail();
    MyNicknameResponse getMyNickname();
}
