package com.dbs.club.domain.meeting.exception;

import com.dbs.club.presentation.common.ErrorCode;
import lombok.Getter;

@Getter
public class MeetingException extends RuntimeException {

    private ErrorCode errorCode;
    public MeetingException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
