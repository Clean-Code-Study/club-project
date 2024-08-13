package com.dbs.club.presentation.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbs.club.domain.common.BusinessException;
import com.dbs.club.domain.common.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(HttpServletRequest request,
        BusinessException businessException) {
        warning(request, businessException);

        final ErrorResponse errorResponse = new ErrorResponse(businessException);
        return ResponseEntity
            .status(errorResponse.getStatus())
            .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        ErrorResponse response = new ErrorResponse(ErrorCode.COMMON_SYSTEM_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidExceptionHandler(HttpServletRequest request,
        MethodArgumentNotValidException exception) {
        warning(request, exception);

        List<String> errorMessages = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.COMMON_INVALID_PARAMETER,
            errorMessages
        );

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    protected void warning(final HttpServletRequest request, final Exception exception) {
        if (log.isWarnEnabled()) {
            log.warn(formattedLog(request), exception);
        }
    }

    private String formattedLog(HttpServletRequest request) {
        return String.format("[Exception] Request : %s", request.getRequestURL());
    }
}
