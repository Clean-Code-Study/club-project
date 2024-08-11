package com.dbs.club.presentation.common;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String code;
    private final List<String> detailedMessage;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.toString();
        this.detailedMessage = null;
    }

    public ErrorResponse(ErrorCode errorCode, List<String> detailedMessage) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.toString();
        this.detailedMessage = detailedMessage;
    }

    public ErrorResponse(HttpStatus status, String errorMessage, String code, List<String> detailedMessage) {
        this.status = status.value();
        this.message = errorMessage;
        this.code = code;
        this.detailedMessage = detailedMessage;
    }

    public ErrorResponse(BusinessException businessException) {
        this(
            businessException.getErrorCode().getStatus(),
            businessException.getMessage(),
            businessException.getErrorCode().toString(),
            null
        );
    }
}
