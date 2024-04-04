package kfoodbox.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionInformation {
    EMAIL_DUPLICATES("중복되는 이메일이 이미 존재합니다.", HttpStatus.CONFLICT),
    NICKNAME_DUPLICATES("중복되는 닉네임이 이미 존재합니다.", HttpStatus.CONFLICT),
    NON_EXISTENT_LANGUAGE("존재하지 않는 언어입니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
