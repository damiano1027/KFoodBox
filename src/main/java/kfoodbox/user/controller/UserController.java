package kfoodbox.user.controller;

import jakarta.validation.Valid;
import kfoodbox.common.response.EmptyResponse;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<EmailExistenceResponse> getExistenceOfEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getExistenceOfEmail(email));
    }

    @PostMapping("/user")
    public ResponseEntity<EmptyResponse> signup(@RequestBody @Valid SignupRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok(new EmptyResponse());
    }

    @GetMapping("/languages")
    public ResponseEntity<LanguagesResponse> getAllLanguages() {
        return ResponseEntity.ok(userService.getAllLanguages());
    }
}
