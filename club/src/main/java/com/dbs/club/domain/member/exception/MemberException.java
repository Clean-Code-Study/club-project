package com.dbs.club.domain.member.exception;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

public class MemberException extends BusinessException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
