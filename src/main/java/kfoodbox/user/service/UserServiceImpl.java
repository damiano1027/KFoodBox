package kfoodbox.user.service;

import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.entity.Language;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void signUp(SignupRequest request) {
        User sameEmailUser = userRepository.findUserByEmail(request.getEmail());
        if (sameEmailUser != null) {
            throw new NonCriticalException(ExceptionInformation.EMAIL_DUPLICATES);
        }

        User sameNicknameUser = userRepository.findUserByNickname(request.getNickname());
        if (sameNicknameUser != null) {
            throw new NonCriticalException(ExceptionInformation.NICKNAME_DUPLICATES);
        }

        Language language = userRepository.findLanguageById(request.getLanguageId());
        if (language == null) {
            throw new NonCriticalException(ExceptionInformation.NON_EXISTENT_LANGUAGE);
        }

        User user = User.from(request);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.changePassword(hashedPassword);

        userRepository.saveUser(user);
    }
}
