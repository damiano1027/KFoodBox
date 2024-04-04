package kfoodbox.user.service;

import kfoodbox.user.dto.response.EmailExistenceResponse;

public interface UserService {
    EmailExistenceResponse getExistenceOfNickname(String email);
}
