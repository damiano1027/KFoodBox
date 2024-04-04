package kfoodbox.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionInformation {
    UNAUTHORIZED("인증된 회원이 아닙니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("권한이 없습니다.", HttpStatus.FORBIDDEN),
    EMAIL_DUPLICATES("중복되는 이메일이 이미 존재합니다.", HttpStatus.CONFLICT),
    NICKNAME_DUPLICATES("중복되는 닉네임이 이미 존재합니다.", HttpStatus.CONFLICT),
    NON_EXISTENT_LANGUAGE("존재하지 않는 언어입니다.", HttpStatus.NOT_FOUND),
    NO_MEMBER("회원 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
