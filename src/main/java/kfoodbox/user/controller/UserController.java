package kfoodbox.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kfoodbox.common.authority.Login;
import kfoodbox.common.authority.Authority;
import kfoodbox.user.dto.request.LoginRequest;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.request.UserUpdateRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.dto.response.MyEmailResponse;
import kfoodbox.user.dto.response.MyNicknameResponse;
import kfoodbox.user.dto.response.NicknameExistenceResponse;
import kfoodbox.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/email-existence")
    public ResponseEntity<EmailExistenceResponse> getExistenceOfEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getExistenceOfEmail(email));
    }

    @GetMapping("/nickname-existence")
    public ResponseEntity<NicknameExistenceResponse> getExistenceOfNickname(@RequestParam("nickname") @NotNull String nickname) {
        return ResponseEntity.ok(userService.getExistenceOfNickname(nickname));
    }

    @PostMapping("/user")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/languages")
    public ResponseEntity<LanguagesResponse> getAllLanguages() {
        return ResponseEntity.ok(userService.getAllLanguages());
    }

    @PostMapping("/user/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        userService.login(request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/user/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @GetMapping("/user/my-email")
    public ResponseEntity<MyEmailResponse> getMyEmail() {
        return ResponseEntity.ok(userService.getMyEmail());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/user/my-nickname")
    public ResponseEntity<MyNicknameResponse> getMyNickname() {
        return ResponseEntity.ok(userService.getMyNickname());
    }

    @Login(Authority.NORMAL)
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        userService.updateUser(request);
        return ResponseEntity.ok(null);
    }
}
