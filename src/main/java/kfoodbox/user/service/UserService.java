package kfoodbox.user.service;

import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;

public interface UserService {
    EmailExistenceResponse getExistenceOfNickname(String email);
    void signUp(SignupRequest request);
    LanguagesResponse getAllLanguages();
}
