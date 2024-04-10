package kfoodbox.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.authority.Login;
import kfoodbox.common.exception.ExceptionInformation;
import kfoodbox.common.exception.NonCriticalException;
import kfoodbox.user.entity.User;
import kfoodbox.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorityInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Login login = handlerMethod.getMethod().getDeclaredAnnotation(Login.class);
        if (login == null) {
            Optional<User> user = findUser(request);
            user.ifPresent(value -> request.setAttribute("userId", value.getId()));
            return true;
        }

        Optional<User> user = findUser(request);
        if (user.isEmpty()) {
            throw new NonCriticalException(ExceptionInformation.UNAUTHORIZED);
        }

        for (Authority authority : login.value()) {
            if (authority.equals(Authority.ADMIN)) {
                if (!user.get().isAdmin()) {
                    throw new NonCriticalException(ExceptionInformation.FORBIDDEN);
                }
            }
        }

        request.setAttribute("userId", user.get().getId());
        return true;
    }

    private Optional<User> findUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return Optional.empty();
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
