package kfoodbox.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kfoodbox.common.authority.Login;
import kfoodbox.common.authority.Authority;
import kfoodbox.common.exception.ExceptionResponse;
import kfoodbox.common.exception.UnprocessableEntityExceptionResponse;
import kfoodbox.user.dto.request.LoginRequest;
import kfoodbox.user.dto.request.SignupAuthenticationNumberSendRequest;
import kfoodbox.user.dto.request.SignupRequest;
import kfoodbox.user.dto.request.UserUpdateRequest;
import kfoodbox.user.dto.response.EmailExistenceResponse;
import kfoodbox.user.dto.response.LanguagesResponse;
import kfoodbox.user.dto.response.MyEmailResponse;
import kfoodbox.user.dto.response.MyLanguageResponse;
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

@Tag(name = "회원", description = "회원 API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/email-existence")
    @Operation(summary = "이메일 존재 여부 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<EmailExistenceResponse> getExistenceOfEmail(@RequestParam("email") @Schema(description = "이메일") String email) {
        return ResponseEntity.ok(userService.getExistenceOfEmail(email));
    }

    @GetMapping("/nickname-existence")
    @Operation(summary = "닉네임 존재 여부 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<NicknameExistenceResponse> getExistenceOfNickname(@RequestParam("nickname") @Schema(description = "닉네임") String nickname) {
        return ResponseEntity.ok(userService.getExistenceOfNickname(nickname));
    }

    @PostMapping("/authentication-number")
    @Operation(summary = "이메일 인증번호 전송 (회원가입)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> sendSignupAuthenticationNumber(@RequestBody @Valid SignupAuthenticationNumberSendRequest request) {
        userService.sendSignupAuthenticationNumber(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/user")
    @Operation(summary = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "언어 정보 없음 (NO_LANGUAGE)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "이메일 중복 (EMAIL_DUPLICATES) | 닉네임 중복 (NICKNAME_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/languages")
    @Operation(summary = "모든 언어 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<LanguagesResponse> getAllLanguages() {
        return ResponseEntity.ok(userService.getAllLanguages());
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음 (NO_USER)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        userService.login(request);
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.ok(null);
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-email")
    @Operation(summary = "나의 이메일 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음 (NO_USER)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyEmailResponse> getMyEmail() {
        return ResponseEntity.ok(userService.getMyEmail());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-nickname")
    @Operation(summary = "나의 닉네임 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음 (NO_USER)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyNicknameResponse> getMyNickname() {
        return ResponseEntity.ok(userService.getMyNickname());
    }

    @Login(Authority.NORMAL)
    @GetMapping("/my-language")
    @Operation(summary = "나의 언어 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음 (NO_USER)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<MyLanguageResponse> getMyLanguage() {
        return ResponseEntity.ok(userService.getMyLanguage());
    }

    @Login(Authority.NORMAL)
    @PutMapping("/user")
    @Operation(summary = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증된 회원이 아님 (UNAUTHORIZED)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음 (FORBIDDEN)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음 (NO_USER)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "닉네임 중복 (NICKNAME_DUPLICATES)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "422", description = "요청 데이터 제약조건 위반 (UNPROCESSABLE_ENTITY)", content = @Content(schema = @Schema(implementation = UnprocessableEntityExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러 (INTERNAL_SERVER_ERROR)", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        userService.updateUser(request);
        return ResponseEntity.ok(null);
    }
}
