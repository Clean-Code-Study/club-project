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
    private final List<String> detailMessage;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.toString();
        this.detailMessage = null;
    }

    public ErrorResponse(ErrorCode errorCode, List<String> detailMessage) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.toString();
        this.detailMessage = detailMessage;
    }

    public ErrorResponse(HttpStatus status, String errorMessage, String code, List<String> detailMessage) {
        this.status = status.value();
        this.message = errorMessage;
        this.code = code;
        this.detailMessage = detailMessage;
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
