package com.dbs.club.domain.meetingjoin.exception;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

public class MeetingJoinException extends BusinessException {
    public MeetingJoinException(ErrorCode errorCode) {
        super(errorCode);
    }
}
