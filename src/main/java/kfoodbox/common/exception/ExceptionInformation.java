package kfoodbox.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionInformation {
    UNAUTHORIZED("인증된 회원이 아닙니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("서버에서 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_DUPLICATES("중복되는 이메일이 이미 존재합니다.", HttpStatus.CONFLICT),
    NICKNAME_DUPLICATES("중복되는 닉네임이 이미 존재합니다.", HttpStatus.CONFLICT),
    NON_EXISTENT_LANGUAGE("존재하지 않는 언어입니다.", HttpStatus.NOT_FOUND),
    NO_USER("회원 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NO_ARTICLE("게시물 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NO_FOOD("음식 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    BOOKMARK_DUPLICATES("북마크 정보가 이미 존재합니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
