package org.kenux.miraclelibrary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이미 가입된 이메일 주소입니다."),
    PASSWORD_SHORT(HttpStatus.BAD_REQUEST, "패스워드는 8자 이상이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}