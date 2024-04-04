package kfoodbox.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.user.dto.request.LoginRequest;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.dto.response.NicknameExistenceResponse;
import kfoodbox.user.entity.Language;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public EmailExistenceResponse getExistenceOfEmail(String email) {
        User sameEmailUser = userRepository.findUserByEmail(email);

        return sameEmailUser == null ?
                new EmailExistenceResponse(false)
                : new EmailExistenceResponse(true);
    }

    @Override
    @Transactional(readOnly = true)
    public NicknameExistenceResponse getExistenceOfNickname(String nickname) {
        User sameNicknameUser = userRepository.findUserByNickname(nickname);

        return sameNicknameUser == null ?
                new NicknameExistenceResponse(false)
                : new NicknameExistenceResponse(true);
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

    @Override
    @Transactional(readOnly = true)
    public LanguagesResponse getAllLanguages() {
        List<Language> languages = userRepository.findAllLanguages();
        return new LanguagesResponse(languages);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());

        if (user == null || !BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new NonCriticalException(ExceptionInformation.NO_MEMBER);
        }

        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        HttpSession session = servletRequest.getSession();
        session.setAttribute("userId", user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public void logout() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        HttpSession session = servletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}
