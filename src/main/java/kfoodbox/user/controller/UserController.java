package kfoodbox.user.controller;

import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<EmailExistenceResponse> getExistenceOfEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.getExistenceOfNickname(email));
    }
}
