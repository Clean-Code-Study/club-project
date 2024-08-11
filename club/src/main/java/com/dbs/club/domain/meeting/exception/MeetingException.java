package com.dbs.club.domain.meeting.exception;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

import lombok.Getter;

@Getter
public class MeetingException extends BusinessException {

    public MeetingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
