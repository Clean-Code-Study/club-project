package com.dbs.club.domain.member.exception;

import com.dbs.club.presentation.common.ErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private ErrorCode errorCode;
    public MemberException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
