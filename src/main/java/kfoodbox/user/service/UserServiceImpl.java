package kfoodbox.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kfoodbox.article.entity.CommunityArticle;
import kfoodbox.article.entity.CustomRecipeArticle;
import kfoodbox.article.repository.CommunityArticleRepository;
import kfoodbox.article.repository.CustomRecipeArticleRepository;
import kfoodbox.common.exception.CriticalException;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.common.request.RequestApproacher;
import kfoodbox.common.util.EmailSender;
import kfoodbox.common.util.RedisClient;
import kfoodbox.user.dto.request.LanguageUpdateRequest;
import kfoodbox.user.dto.request.LoginRequest;
import kfoodbox.user.dto.request.SignupAuthenticationNumberSendRequest;
import kfoodbox.user.dto.request.SignupAuthenticationNumberVerityRequest;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.request.UserUpdateRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.dto.response.MyArticlesResponse;
import kfoodbox.user.dto.response.MyEmailResponse;
import kfoodbox.user.dto.response.MyLanguageResponse;
import kfoodbox.user.dto.response.MyNicknameResponse;
import kfoodbox.user.dto.response.NicknameExistenceResponse;
import kfoodbox.user.entity.Language;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CommunityArticleRepository communityArticleRepository;
    private final CustomRecipeArticleRepository customRecipeArticleRepository;
    private final EmailSender emailSender;
    private final RedisClient redisClient;

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
    public void sendSignupAuthenticationNumber(SignupAuthenticationNumberSendRequest request) {
        String authenticationNumber = RandomStringUtils.randomNumeric(6);
        redisClient.putSignupAuthenticationNumber(request.getEmail(), authenticationNumber);
        emailSender.sendAuthenticationNumber(request.getEmail(), authenticationNumber);
    }

    @Override
    public void verifySignupAuthenticationNumber(SignupAuthenticationNumberVerityRequest request) {
        // brute force 공격을 막기 위해 조회시 바로 삭제한다. (한번의 인증 메일 전송에 대해 1번의 인증 기회만 부여)
        Optional<String> authenticationNumberInRedis = redisClient.getSignupAuthenticationNumberAndDelete(request.getEmail());

        if (authenticationNumberInRedis.isEmpty()) {
            throw new NonCriticalException(ExceptionInformation.BAD_ACCESS);
        }

        String authenticationNumber = authenticationNumberInRedis.get();
        if (!authenticationNumber.equals(request.getAuthenticationNumber())) {
            throw new NonCriticalException(ExceptionInformation.BAD_ACCESS);
        }
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
            throw new NonCriticalException(ExceptionInformation.NO_LANGUAGE);
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
            throw new NonCriticalException(ExceptionInformation.NO_USER);
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

    @Override
    @Transactional(readOnly = true)
    public MyEmailResponse getMyEmail() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NonCriticalException(ExceptionInformation.NO_USER);
        }

        return new MyEmailResponse(user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public MyNicknameResponse getMyNickname() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NonCriticalException(ExceptionInformation.NO_USER);
        }

        return new MyNicknameResponse(user.getNickname());
    }

    @Override
    @Transactional(readOnly = true)
    public MyLanguageResponse getMyLanguage() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NonCriticalException(ExceptionInformation.NO_USER);
        }

        return new MyLanguageResponse(user.getLanguageId());
    }

    @Override
    @Transactional
    public void updateMyLanguage(LanguageUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NonCriticalException(ExceptionInformation.NO_USER);
        }

        Language language = userRepository.findLanguageById(request.getLanguageId());
        if (Objects.isNull(language)) {
            throw new NonCriticalException(ExceptionInformation.NO_LANGUAGE);
        }

        user.changeLanguageId(request.getLanguageId());
        userRepository.updateUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateRequest request) {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        User sameNicknameUser = userRepository.findUserByNickname(request.getNickname());
        if (sameNicknameUser != null && !sameNicknameUser.isIdSame(userId)) {
            throw new NonCriticalException(ExceptionInformation.NICKNAME_DUPLICATES);
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NonCriticalException(ExceptionInformation.NO_USER);
        }

        user.update(request);
        userRepository.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (userId == null) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        userRepository.deleteUserById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public MyArticlesResponse getMyArticles() {
        HttpServletRequest servletRequest = RequestApproacher.getHttpServletRequest();
        Long userId = (Long) servletRequest.getAttribute("userId");

        if (Objects.isNull(userId)) {
            throw new CriticalException(ExceptionInformation.INTERNAL_SERVER_ERROR);
        }

        List<CommunityArticle> communityArticles = communityArticleRepository.findCommunityArticlesByUserId(userId);
        List<CustomRecipeArticle> customRecipeArticles = customRecipeArticleRepository.findCustomRecipeArticlesByUserId(userId);

        return MyArticlesResponse.of(communityArticles, customRecipeArticles);
    }
}
