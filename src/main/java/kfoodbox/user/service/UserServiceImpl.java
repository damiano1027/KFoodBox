package kfoodbox.user.service;

import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public EmailExistenceResponse getExistenceOfNickname(String email) {
        User sameEmailUser = userRepository.findUserByEmail(email);

        return sameEmailUser == null ?
                new EmailExistenceResponse(false)
                : new EmailExistenceResponse(true);
    }
}
